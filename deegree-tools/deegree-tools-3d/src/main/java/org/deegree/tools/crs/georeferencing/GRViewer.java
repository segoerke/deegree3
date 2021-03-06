//$HeadURL$$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2009 by:
 - Department of Geography, University of Bonn -
 and
 - lat/lon GmbH -

 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Contact information:

 lat/lon GmbH
 Aennchenstr. 19, 53177 Bonn
 Germany
 http://lat-lon.de/

 Department of Geography, University of Bonn
 Prof. Dr. Klaus Greve
 Postfach 1147, 53001 Bonn
 Germany
 http://www.geographie.uni-bonn.de/deegree/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/

package org.deegree.tools.crs.georeferencing;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static org.deegree.tools.crs.georeferencing.communication.GUIConstants.FRAME_DIMENSION;
import static org.deegree.tools.crs.georeferencing.i18n.Messages.get;

import javax.swing.JFrame;

import org.deegree.commons.annotations.Tool;
import org.deegree.tools.crs.georeferencing.application.ApplicationState;
import org.deegree.tools.crs.georeferencing.application.Controller;
import org.deegree.tools.crs.georeferencing.communication.DefaultGRViewerGUI;
import org.deegree.tools.crs.georeferencing.communication.GRViewerGUI;

/**
 * 
 * Initialisation class that opens a GUI to transform non-georeferenced buildings into a georeferencing map.
 * 
 * @author <a href="mailto:thomas@lat-lon.de">Steffen Thomas</a>
 * @author last edited by: $Author$
 * 
 * @version $Revision$, $Date$
 */
@Tool("Initializes the georeferencing tool. ")
public class GRViewer {

    /**
     * @param args
     */
    public static void main( String[] args ) {
        ApplicationState state = new ApplicationState();
        state.systemExitOnClose = true;

        JFrame frame = new JFrame( get( "WINDOW_TITLE" ) );
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.setMinimumSize( FRAME_DIMENSION );
        frame.setPreferredSize( FRAME_DIMENSION );
        frame.pack();

        GRViewerGUI gui = new DefaultGRViewerGUI( state, frame.getContentPane(), frame, frame.getRootPane(), frame );

        new Controller( gui, state );

        frame.setVisible( true );

    }

}
