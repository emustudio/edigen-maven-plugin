/*
 * Copyright (C) 2012 Matúš Sulír
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.emustudio.edigen.maven;

import net.emustudio.edigen.Edigen;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;

/**
 * Edigen Maven plugin.
 *
 * @author Matúš Sulír
 */
@SuppressWarnings("unused")
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class EdigenGenerator extends AbstractMojo {

    /**
     * An object representing the Maven project which uses this plugin in its
     * POM file.
     */
    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    /**
     * Specification file for Edigen.
     */
    @Parameter(defaultValue = "${basedir}/src/main/edigen/cpu.eds")
    private File specification;

    /**
     * Instruction decoder package + class name.
     */
    @Parameter(required = true)
    private String decoderName;

    /**
     * Disassembler package + class name.
     */
    @Parameter(required = true)
    private String disassemblerName;

    /**
     * Disassembler output directory.
     * <p>
     * The default is target/generated-sources/edigen/ + path obtained from
     * the package name.
     */
    @Parameter
    private File disassemblerOutputDir;

    /**
     * Disassembler template file.
     */
    @Parameter
    private File disassemblerTemplate;

    /**
     * Debug mode (display tree transformations).
     */
    @Parameter
    private boolean debug;

    /**
     * Instruction decoder output directory.
     * <p>
     * The default is target/generated-sources/edigen/ + path obtained from
     * the package name.
     */
    @Parameter
    private File decoderOutputDir;

    /**
     * Instruction decoder template file.
     */
    @Parameter
    private File decoderTemplate;

    /**
     * A list of command-line arguments passed to Edigen.
     */
    private ArgumentList arguments;

    /**
     * Executes the goal "generate" of this plugin.
     *
     * @throws MojoExecutionException when the goal execution is unsuccessful
     */
    @Override
    public void execute() throws MojoExecutionException {
        validate();

        arguments = new ArgumentList(project);
        addArguments();

        try {
            new Edigen().run(arguments.get());
        } catch (Exception ex) {
            throw new MojoExecutionException(ex.getMessage(), ex);
        }
    }

    /**
     * Validates the project configuration.
     *
     * @throws MojoExecutionException when the configuration is invalid
     */
    private void validate() throws MojoExecutionException {
        if (!decoderName.contains("."))
            throw new MojoExecutionException("Decoder name must include a package.");

        if (!disassemblerName.contains("."))
            throw new MojoExecutionException("Disassembler name must include a package.");
    }

    /**
     * Adds command-line arguments to the list, according to the configuration.
     */
    private void addArguments() {
        arguments.add(specification.getPath());
        arguments.add(decoderName);
        arguments.add(disassemblerName);

        arguments.addOutputDirectory("-ao", disassemblerOutputDir, disassemblerName);
        arguments.addTemplate("-at", disassemblerTemplate);

        arguments.addFlag("-d", debug);

        arguments.addOutputDirectory("-do", decoderOutputDir, decoderName);
        arguments.addTemplate("-dt", decoderTemplate);
    }
}
