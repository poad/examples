package org.bitbucket.poad1010.example.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.RestartPolicy;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Created by ken-yo on 2016/08/08.
 */
public class Application {
    private static final String IMAGE = "mysql";
    private static final String NAME = "mysql";

    public static void main(String... args) throws InterruptedException {
        DockerClient client = buildDockerClient();

        String containerID = setUpDockerContainer(client);

        ExecCreateCmdResponse execCreateCmdResponse = client.execCreateCmd(containerID)
                .withCmd("ping", "8.8.8.8", "-c", "30")
                .exec();
        client.execStartCmd(execCreateCmdResponse.getId()).exec(
                new ExecStartResultCallback(System.out, System.err)).awaitCompletion();

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/testdb?useSSL=true");
        ds.setUsername("root");


        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        flyway.migrate();

        tearDownDockerContainer(client, containerID);
    }

    private static DockerClient buildDockerClient() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withApiVersion("1.23")
                .withRegistryUrl("https://index.docker.io/v1/")
                .build();

        // using jaxrs/jersey implementation here (netty impl is also available)
        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
                .withReadTimeout(59000)
                .withConnectTimeout(1000)
                .withMaxTotalConnections(100)
                .withMaxPerRouteConnections(10);

        return DockerClientBuilder.getInstance(config)
                .withDockerCmdExecFactory(dockerCmdExecFactory)
                .build();
    }

    private static String setUpDockerContainer(DockerClient client) {
        List<Image> images = client.listImagesCmd()
                .withImageNameFilter(IMAGE)
                .exec();

        if (!images.stream().findFirst().isPresent()) {
            List<SearchItem> dockerSearch = client.searchImagesCmd(IMAGE).exec();
            dockerSearch.stream().filter(item -> item.isOfficial())
                    .findFirst().map(item -> item.getName()).ifPresent(name -> {
                try {
                    client.pullImageCmd(name).exec(new PullImageResultCallback()).awaitCompletion();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        List<Container> containers = client.listContainersCmd().withShowAll(true).exec();
        String containerID = containers.stream()
                .filter(container -> container.getImage().equals(IMAGE) && Arrays.asList(container.getNames()).contains("/" + NAME))
                .findFirst()
                .map(Container::getId).orElseGet(() -> client.createContainerCmd(IMAGE)
                        .withName(NAME)
                        .withEnv(Arrays.asList("MYSQL_USER=root", "MYSQL_ALLOW_EMPTY_PASSWORD=yes", "MYSQL_DATABASE=testdb"))
                        .withRestartPolicy(RestartPolicy.alwaysRestart())
                        .withNetworkMode("host")
                        .withCmd("--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci")
//                        .withPortBindings(new PortBinding(Ports.Binding.bindIpAndPort("0.0.0.0", 3306), ExposedPort.tcp(3306)))
                        .withPortSpecs("0.0.0.0:3306:3306/tcp")
                        .exec()
                        .getId());

        System.out.println(containerID);

        Predicate<Container> filter = (container) -> container.getImage().equals(IMAGE) && Arrays.asList(container.getNames()).contains("/" + NAME);
        client.listContainersCmd()
                .withShowAll(true)
                .exec()
                .stream()
                .filter(filter)
                .findFirst()
                .ifPresent(container -> {
                    System.out.println(container);
                    Arrays.asList(container.getPorts()).forEach(System.out::println);
                });

        if (!client.listContainersCmd().exec().stream()
                .filter(filter)
                .findFirst()
                .isPresent()) {
            client.startContainerCmd(containerID).exec();
        }
        return containerID;
    }

    private static void tearDownDockerContainer(DockerClient client, String containerID) {
        client.stopContainerCmd(containerID).exec();
        Integer code = client.waitContainerCmd(containerID).exec(new WaitContainerResultCallback()).awaitStatusCode(60L, TimeUnit.SECONDS);
        System.out.println(code);
        client.removeContainerCmd(containerID);
    }
}
