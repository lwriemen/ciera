package io.ciera.maven;

import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import org.apache.maven.project.MavenProject;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.tool.DynamoDBTool;

import java.io.File;

/**
 * Goal which runs the DynamoDB builder.
 */
@Mojo(name="dynamodb", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class DynamoDBMojo extends AbstractCieraMojo {

    @Parameter
    private String input;

    @Parameter
    private String output;

    @Parameter(readonly=true, defaultValue="${project}")
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        String projectDir = getProject().getBasedir().getPath();
        String inFile = null == input ? "" : new File(projectDir).toURI().relativize(new File(input).toURI()).getPath();
        String outFile = null == output ? "" : new File(projectDir).toURI().relativize(new File(output).toURI()).getPath();
        IApplication app = new DynamoDBTool();
        app.setup(new String[]{"-i", inFile, "-o", outFile, "--cwd", projectDir}, new CieraMavenLogger(getLog()));
        app.initialize();
        app.start();
        waitForThreads();
        copyCustomCode();
        addSrcGen();
        refreshFiles();
    }

    public MavenProject getProject() {
        return project;
    }

}
