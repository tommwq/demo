package com.tommwq;

import org.apache.maven.model.*;
import org.apache.maven.project.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;

import java.io.*;

@Mojo(name = "generate-build-info", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.RUNTIME, requiresProject = true, threadSafe = false)
public class GenerateBuildInfoMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;
    
    public void execute() throws MojoExecutionException {
        try {
            new BuildInfoGenerator().generate(project);
        } catch (Exception e) {
            throw new MojoExecutionException("Error creating BuildInfo.java", e);
        }
    }
}
