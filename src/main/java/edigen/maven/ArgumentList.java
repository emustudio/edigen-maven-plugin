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
package edigen.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.project.MavenProject;

/**
 * A command-line argument list for edigen.
 * @author Matúš Sulír
 */
public class ArgumentList {
    
    private List<String> arguments = new ArrayList<String>();
    private MavenProject project;

    /**
     * Constructs the argument list.
     * @param project the Maven project
     */
    public ArgumentList(MavenProject project) {
        this.project = project;
    }
    
    /**
     * Adds a command-line argument to the argument list.
     * @param argument the parameter
     */
    public void add(String argument) {
        arguments.add(argument);
    }
    
    /**
     * Returns an array containing all arguments.
     * @return the array of arguments
     */
    public String[] get() {
        return arguments.toArray(new String[0]);
    }
    
    /**
     * Adds the argument representing the output directory to the list of
     * arguments.
     * @param argument the argument name (e.g. "-ao")
     * @param directory the directory supplied by a user in the configuration
     *        file (can be null)
     * @param packageAndClass the package + class name of the generated source
     *        code
     */
    public void addOutputDirectory(String argument, File directory, String packageAndClass) {
        if (directory == null)
            directory = extractDirectory(packageAndClass);
        
        directory.mkdirs();
        project.addCompileSourceRoot(directory.getPath());
        
        add(argument);
        add(directory.getPath());
    }
    
    /**
     * Creates the directory name from the Java package + class name.
     * @param packageAndClass the package + class name
     * @return the directory of the package
     */
    private File extractDirectory(String packageAndClass) {
        StringBuilder path = new StringBuilder(project.getBuild().getDirectory());
        path.append(File.separator).append("generated-sources")
            .append(File.separator).append("edigen");
        
        int dotIndex = packageAndClass.lastIndexOf('.');
        String subdirectories = packageAndClass.substring(0, dotIndex).replace('.', '/');
        path.append(File.separator).append(subdirectories);
        
        return new File(path.toString());
    }
}
