package org.bitbucket.poad1010.examples.kotlin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer;
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler;
import org.jetbrains.kotlin.cli.jvm.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.script.KotlinScriptDefinition;
import org.jetbrains.kotlin.script.StandardScriptDefinition;
import org.jetbrains.kotlin.utils.KotlinPaths;
import org.jetbrains.kotlin.utils.KotlinPathsFromHomeDir;
import org.jetbrains.kotlin.utils.PathUtil;

import com.intellij.openapi.Disposable;

public class KotlinDynamicCompiler {
	private static final KotlinToJVMBytecodeCompiler COMPILER = KotlinToJVMBytecodeCompiler.INSTANCE;
	private Disposable disposable = new Disposable() {
		@Override
		public void dispose() {
		}
	};

	public Class<?> compile(File dir) {
		KotlinPaths paths = new KotlinPathsFromHomeDir(dir);
		CompilerConfiguration configuration = new CompilerConfiguration();
		MessageCollector messageCollector = new PrintingMessageCollector(System.err, MessageRenderer.PLAIN_FULL_PATHS, false);
        configuration.put(JVMConfigurationKeys.DISABLE_PARAM_ASSERTIONS, false);
        configuration.put(JVMConfigurationKeys.DISABLE_INLINE, false);

        configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, messageCollector);
        List<KotlinScriptDefinition> scriptDefinition = Arrays.asList(StandardScriptDefinition.INSTANCE);
        configuration.put(CommonConfigurationKeys.SCRIPT_DEFINITIONS_KEY, scriptDefinition);
        PathUtil.getKotlinPathsForCompiler();
        
		KotlinCoreEnvironment environment = createEnvironment(paths);
		COMPILER.messageCollector(environment);
		return COMPILER.compileScript(configuration, paths, environment);
	}

	private KotlinCoreEnvironment createEnvironment(KotlinPaths paths) {
		K2JVMCompilerArguments arguments = new K2JVMCompilerArguments();
		CompilerConfiguration configuration = new CompilerConfiguration();

		JvmContentRootsKt.addJvmClasspathRoots(configuration, getClasspath(paths, arguments));

		configuration.put(JVMConfigurationKeys.DISABLE_PARAM_ASSERTIONS, arguments.noParamAssertions);
		configuration.put(JVMConfigurationKeys.DISABLE_CALL_ASSERTIONS, arguments.noCallAssertions);

		KotlinCoreEnvironment environment = KotlinCoreEnvironment.createForProduction(disposable, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES);
		return environment;
	}

	private List<File> getClasspath(KotlinPaths paths, K2JVMCompilerArguments arguments) {
        List<File> classpath = new ArrayList<>();
        classpath.addAll(PathUtil.getJdkClassesRoots());
        classpath.add(paths.getRuntimePath());
        String classPath = arguments.classpath;
        if (classPath != null) {
            for (String element : classPath.split(File.pathSeparator)) {
                classpath.add(new File(element));
            }
        }
        return classpath;
    }
}
