/**
 * Copyright 1998-2001 Sun Microsystems, Inc.
 * 
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL 
 * WARRANTIES.
 */
package com.sun.speech.engine.synthesis;

import java.awt.Component;import java.awt.event.*;

import javax.swing.JOptionPane;

import java.util.Vector;
import java.util.Enumeration;

import javax.speech.Central;
import javax.speech.EngineList;
import javax.speech.synthesis.SynthesizerModeDesc;

/**
 * GUI that displays a list of <code>Synthesizer</code> names in a
 * dialog box.  When the user makes a selection, the selected
 * <code>SynthesizerModeDesc</code> is returned to the caller.
 */
public class SynthesizerSelector {
    /**
     * Asks <code>Central</code> for a list of available synthesizers.
     * If there are none, returns <code>null</code>.  If there is only
     * one, returns it.  Otherwise, pops up an input dialog that gives
     * the user a choice.
     *
     * @param component the component for JOptionPane.showInputDialog
     * @param appName the title for the input dialog
     *
     * @return a <code>SynthesizerModeDesc</code> representing the
     *   synthesizer to use.
     */
    static public SynthesizerModeDesc getSynthesizerModeDesc(
        Component component,
	String appName) {
	Vector synths = new Vector();
	Vector synthNames = new Vector();
	EngineList list = Central.availableSynthesizers(null); 
	Enumeration e = list.elements();
	while (e.hasMoreElements()) {
            synths.addElement(((SynthesizerModeDesc) e.nextElement()));
            synthNames.addElement(
                ((SynthesizerModeDesc)
                 synths.elementAt(synths.size() - 1)).getEngineName());
        }
	Object[] synthNamesArray = synthNames.toArray();

        if (synths.size() == 0) {
            return null;
        } else if (synths.size() == 1) {
            return (SynthesizerModeDesc) synths.elementAt(0);
        }
        
	String synthName = (String)JOptionPane.showInputDialog(
	    component,
	    "Select the Synthesizer to use:",
	    appName + ": Select Synthesizer",
	    JOptionPane.QUESTION_MESSAGE,
	    null,
            synthNamesArray,
	    synthNamesArray[0]);

	int index = synthNames.indexOf(synthName);
	if (index == -1) {
	    return null;
	}
        return (SynthesizerModeDesc) synths.elementAt(index);
    }
}