package com.github.poad.example.doma2.resources;

import com.github.poad.example.doma2.DatabaseConfig;
import com.github.poad.example.doma2.dao.*;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DatabaseResource {

    @GET
    @Path("index.json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> all() {
        TestDao dao = new TestDaoImpl();

        DatabaseConfig db = DatabaseConfig.singleton();
        TransactionManager tm = db.getTransactionManager();
        return tm.required(() -> dao
                .all()
                .stream()
                .map(t -> t.naem)
                .collect(Collectors.toList()));
    }

    @Path("{id}")
    public String get(@PathParam("id") String id) {
        TestDao dao = new TestDaoImpl();
        DatabaseConfig db = DatabaseConfig.singleton();
        TransactionManager tm = db.getTransactionManager();
        return tm.required(() -> Optional.ofNullable(
                dao.get(id))
                .map(t -> t.naem)
                .orElse(""));
    }
}
