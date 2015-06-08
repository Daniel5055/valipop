/*
 * Copyright 2015 Digitising Scotland project:
 * <http://digitisingscotland.cs.st-andrews.ac.uk/>
 *
 * This file is part of the module record_classification.
 *
 * record_classification is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * record_classification is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with record_classification. If not, see
 * <http://www.gnu.org/licenses/>.
 */
/*
 * | ______________________________________________________________________________________________ | Understanding
 * Scotland's People (USP) project. | | The aim of the project is to produce a linked pedigree for all publicly | |
 * available Scottish birth/death/marriage records from 1855 to the present day. | | | | Digitization of the records is
 * being carried out by the ESRC-funded Digitising | | Scotland project, run by University of St Andrews and National
 * Records of Scotland. | | | | The project is led by Chris Dibben at the Longitudinal Studies Centre at St Andrews. | |
 * The other project members are Lee Williamson (also at the Longitudinal Studies Centre) | | Graham Kirby, Alan Dearle
 * and Jamie Carson at the School of Computer Science at St Andrews; | | and Eilidh Garret and Alice Reid at the
 * Department of Geography at Cambridge. | | | |
 * ______________________________________________________________________________________________
 */
package old.record_classification_old.tools.fileutils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Removes .DS_Store files.
 * 
 * @author jkc25
 */
public class DSStoreRemover {

    /**
     * Constructs a .DS_Strore remover.
     */
    public DSStoreRemover() {

    }

    private int remove(final File folder, final List<File> list) {

        int filesRemoved = 0;

        File[] files = folder.listFiles();
        if (files != null) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].isFile()) {
                    list.add(files[j]);
                    if (files[j].getName().startsWith(".DS_Store")) {
                        if (!files[j].delete()) {
                            System.err.print("Something has gone wrong with the deletion");
                        }
                    }
                }
                if (files[j].isDirectory()) {
                    remove(files[j], list);
                }
            }
        }
        return filesRemoved;
    }

    /**
     * Removes DS_Store files from the given folder.
     * @param folderToCheck folder to remove files from.
     */
    public void remove(final File folderToCheck) {

        File folder = folderToCheck;
        List<File> list = new ArrayList<File>();
        remove(folder, list);
    }

}
