package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int lineNumber = 1;

  private boolean backR = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    for(int i = off; i < off + len; i++){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    //case of the first line
    if (lineNumber == 1) {
      //2 is for the length of lineNumber which is 1 and \t
      super.write(lineNumber + "\t", 0, 2);
      lineNumber++;
    }

    if(c == '\r'){
      backR = true;
      //we only write one character
      super.write(c);
    }else if(c == '\n'){
      backR = false;
      //2 is for the length of \n and \t
      super.write("\n" + lineNumber + "\t", 0, String.valueOf(lineNumber).length() + 2);
      lineNumber++;
    }else{
      if(backR){
        //1 is for the length of \t
        super.write(lineNumber + "\t", 0,String.valueOf(lineNumber).length() + 1 );
        backR = false;
        lineNumber++;
      }
      super.write(c);
    }
  }

}
