package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    //we start at the root
    vistor.visit(rootDirectory);

    File[] childrenFiles = rootDirectory.listFiles();

    Arrays.sort(childrenFiles);

    if(childrenFiles != null){
        for(int i = 0; i < childrenFiles.length; i++){
            if(childrenFiles[i].isFile()){
                vistor.visit(childrenFiles[i]);
            }else{
                explore(childrenFiles[i], vistor);
            }
        }
    }
  }
}
