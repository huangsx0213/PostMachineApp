package PostMachineApp.UserInterface;
// A simple Java Console for your application (Swing version)
// Requires Java 1.1.5 or higher
//
// Disclaimer the use of this source is at your own risk. 
//
// Permision to use and distribute into your own applications
//
// RJHM van den Bergh , rvdb@comweb.nl

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PostMachineConsole extends WindowAdapter implements WindowListener, ActionListener, Runnable {

    private JFrame frame;
    private JTextArea textArea;
    private Thread reader;
    private Thread reader2;
    private boolean quit;

    private final PipedInputStream pin = new PipedInputStream();
    private final PipedInputStream pin2 = new PipedInputStream();

    Thread errorThrower; // just for testing (Throws an Exception at this Console

    public PostMachineConsole(JTextArea jTextAreaConsole) {
        textArea = jTextAreaConsole;
        //textArea=new JTextArea();
        textArea.setEditable(false);

        try {
            PipedOutputStream pout = new PipedOutputStream(this.pin);
            System.setOut(new PrintStream(pout, true));
        } catch (java.io.IOException io) {
            textArea.append("Couldn't redirect STDOUT to this console\n" + io.getMessage());
        } catch (SecurityException se) {
            textArea.append("Couldn't redirect STDOUT to this console\n" + se.getMessage());
        }

        try {
            PipedOutputStream pout2 = new PipedOutputStream(this.pin2);
            System.setErr(new PrintStream(pout2, true));
        } catch (java.io.IOException io) {
            textArea.append("Couldn't redirect STDERR to this console\n" + io.getMessage());
        } catch (SecurityException se) {
            textArea.append("Couldn't redirect STDERR to this console\n" + se.getMessage());
        }

        quit = false; // signals the Threads that they should exit

        // Starting two seperate threads to read from the PipedInputStreams				
        //
        reader = new Thread(this);
        reader.setDaemon(true);
        reader.start();
        //
        reader2 = new Thread(this);
        reader2.setDaemon(true);
        reader2.start();
    }

    public synchronized void actionPerformed(ActionEvent evt) {
        textArea.setText("");
    }

    public synchronized void run() {
        try {
            while (Thread.currentThread() == reader) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin.available() != 0) {
                    String input = this.readLine(pin);
                    textArea.append(input);
                    textArea.setCaretPosition(textArea.getText().length());
                }
                if (quit) {
                    return;
                }
            }

            while (Thread.currentThread() == reader2) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin2.available() != 0) {
                    String input = this.readLine(pin2);
                    textArea.append(input);
                    textArea.setCaretPosition(textArea.getText().length());
                }
                if (quit) {
                    return;
                }
            }
        } catch (Exception e) {
            textArea.append("\nConsole reports an Internal error.");
            textArea.append("The error is: " + e);
        }

        // just for testing (Throw a Nullpointer after 1 second)
        if (Thread.currentThread() == errorThrower) {
            try {
                this.wait(1000);
            } catch (InterruptedException ie) {
            }
            throw new NullPointerException("Application test: throwing an NullPointerException It should arrive at the console");
        }

    }

    public synchronized String readLine(PipedInputStream in) throws IOException {
        String input = "";
        do {
            int available = in.available();
            if (available == 0) {
                break;
            }
            byte b[] = new byte[available];
            in.read(b);
            input = input + new String(b, 0, b.length);
        } while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
        return input;
    }

}
