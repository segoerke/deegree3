//$HeadURL: svn+ssh://aschmitz@wald.intevation.org/deegree/deegree3/trunk/deegree-tools/deegree-tools-3d/src/main/java/org/deegree/tools/crs/georeferencing/communication/GRViewerGUI.java $
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
package org.deegree.tools.crs.georeferencing.communication;

import static java.awt.GridBagConstraints.CENTER;
import static org.deegree.tools.crs.georeferencing.i18n.Messages.get;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import org.deegree.rendering.r3d.opengl.display.OpenGLEventHandler;
import org.deegree.tools.crs.georeferencing.application.ApplicationState;
import org.deegree.tools.crs.georeferencing.application.listeners.ToolbarListener;
import org.deegree.tools.crs.georeferencing.communication.checkboxlist.CheckboxListTransformation;
import org.deegree.tools.crs.georeferencing.communication.panel2D.BuildingFootprintPanel;
import org.deegree.tools.crs.georeferencing.communication.panel2D.Scene2DPanel;

/**
 * The <Code>GRViewerGUI</Code> class provides the client to view georeferencing tools/windows.
 * 
 * @author <a href="mailto:thomas@lat-lon.de">Steffen Thomas</a>
 * @author last edited by: $Author: aschmitz $
 * 
 * @version $Revision: 29058 $, $Date: 2011-01-04 16:37:53 +0100 (Tue, 04 Jan 2011) $
 */
public class DefaultGRViewerGUI implements GRViewerGUI {

    private final static Dimension SUBCOMPONENT_DIMENSION = new Dimension( 1, 1 );

    private Scene2DPanel scenePanel2D;

    private JPanel panelWest, panelEast;

    private OpenGLEventHandler openGLEventListener;

    private BuildingFootprintPanel footprintPanel;

    private JMenuItem editMenuItem, openShape, openWMS, openBuilding, exit, saveBuilding;

    private JMenu menuTransformation;

    private CheckboxListTransformation list;

    private ApplicationState state;

    private Frame parent;

    private Container contentPane;

    private final JRootPane rootPane;

    private final Component resizeComponent;

    private final JSplitPane splitPane;

    /**
     * @param state
     *            the application state
     * @param contentPane
     *            the content pane for all the components
     * @param parent
     *            will be used as parent for dialogs etc.
     * @param rootPane
     *            will be used to register menu
     * @param resizeComponent
     *            a component that a resizing listener will be registered into
     */
    public DefaultGRViewerGUI( ApplicationState state, Container contentPane, Frame parent, JRootPane rootPane,
                               Component resizeComponent ) {
        this.parent = parent;
        this.state = state;
        this.contentPane = contentPane;
        this.rootPane = rootPane;
        this.resizeComponent = resizeComponent;
        this.splitPane = new JSplitPane();
        splitPane.setResizeWeight( .7 );
        contentPane.setLayout( new GridBagLayout() );

        setupMenubar();

        JToolBar bar = new JToolBar();

        String refPng = "/org/deegree/tools/crs/georeferencing/communication/icons/reference.png";
        String panPng = "/org/deegree/tools/crs/georeferencing/communication/icons/pan.png";
        String zoomInPng = "/org/deegree/tools/crs/georeferencing/communication/icons/zoomin.png";
        String zoomOutPng = "/org/deegree/tools/crs/georeferencing/communication/icons/zoomout.png";
        String zoomCoordPng = "/org/deegree/tools/crs/georeferencing/communication/icons/zoombycoord.png";
        String zoomExtent = "/org/deegree/tools/crs/georeferencing/communication/icons/zoomtoextent.png";

        ImageIcon iconReference = new ImageIcon( DefaultGRViewerGUI.class.getResource( refPng ) );
        ImageIcon iconPan = new ImageIcon( DefaultGRViewerGUI.class.getResource( panPng ) );
        ImageIcon iconZoomIn = new ImageIcon( DefaultGRViewerGUI.class.getResource( zoomInPng ) );
        ImageIcon iconZoomOut = new ImageIcon( DefaultGRViewerGUI.class.getResource( zoomOutPng ) );
        ImageIcon iconZoomCoord = new ImageIcon( DefaultGRViewerGUI.class.getResource( zoomCoordPng ) );
        ImageIcon iconZoomExtent = new ImageIcon( DefaultGRViewerGUI.class.getResource( zoomExtent ) );

        // whyever the f*** JRadioButtons do not work here?!?
        JToggleButton pan = new JToggleButton( iconPan );
        JToggleButton zoomIn = new JToggleButton( iconZoomIn );
        JToggleButton zoomOut = new JToggleButton( iconZoomOut );
        JToggleButton reference = new JToggleButton( iconReference );
        JButton zoomToCoordinate = new JButton( iconZoomCoord );
        JButton zoomToMaxExtent = new JButton( iconZoomExtent );
        ButtonGroup g = new ButtonGroup();
        g.add( zoomIn );
        g.add( zoomOut );
        g.add( pan );
        g.add( reference );
        bar.add( zoomIn, true );
        bar.add( zoomOut );
        bar.add( pan );
        bar.add( reference );
        bar.add( zoomToCoordinate );
        bar.add( zoomToMaxExtent );
        bar.setFloatable( false );
        new ToolbarListener( state, pan, zoomIn, zoomOut, reference, zoomToCoordinate, zoomToMaxExtent );

        GridBagLayoutHelper.addComponent( contentPane, bar, 0, 0, 2, 1, 1, 0 );

        setup2DScene();
        setupPanelFootprint();
        setupOpenGL( false );
    }

    @Override
    public Frame getParent() {
        return parent;
    }

    private void setupMenubar() {

        JMenuBar menuBar;
        JMenu menuFile;
        JMenu menuEdit;

        menuBar = new JMenuBar();
        menuFile = new JMenu( get( "MENU_FILE" ) );
        menuEdit = new JMenu( get( "MENU_EDIT" ) );
        menuTransformation = new JMenu( get( "MENU_TRANSFORMATION" ) );

        menuBar.add( menuFile );
        menuBar.add( menuEdit );
        menuBar.add( menuTransformation );

        openShape = new JMenuItem( get( "MENUITEM_OPEN_SHAPEFILE" ) );
        openShape.setName( get( "MENUITEM_OPEN_SHAPEFILE" ) );
        openWMS = new JMenuItem( get( "MENUITEM_OPEN_WMS_LAYER" ) );
        openWMS.setName( get( "MENUITEM_OPEN_WMS_LAYER" ) );
        openBuilding = new JMenuItem( get( "MENUITEM_OPEN_BUILDING" ) );
        openBuilding.setName( get( "MENUITEM_OPEN_BUILDING" ) );
        saveBuilding = new JMenuItem( get( "MENUITEM_SAVE_BUILDING" ) );
        saveBuilding.setName( get( "MENUITEM_SAVE_BUILDING" ) );
        exit = new JMenuItem( get( "MENUITEM_EXIT" ) );
        exit.setName( get( "MENUITEM_EXIT" ) );

        menuFile.add( openShape );
        menuFile.add( openWMS );
        menuFile.add( openBuilding );
        menuFile.add( saveBuilding );
        menuFile.add( exit );

        editMenuItem = new JMenuItem( get( "MENUITEM_EDIT_OPTIONS" ) );
        menuEdit.add( editMenuItem );

        rootPane.setJMenuBar( menuBar );
    }

    private void setup2DScene() {
        panelWest = new JPanel( new GridBagLayout() );
        scenePanel2D = new Scene2DPanel( state );
        scenePanel2D.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
        scenePanel2D.setPreferredSize( SUBCOMPONENT_DIMENSION );

        GridBagLayoutHelper.addComponent( panelWest, scenePanel2D, 0, 0, 0, 0, 1.0, 1.0 );
        panelWest.setMinimumSize( new Dimension( 0, 0 ) );
        splitPane.setLeftComponent( panelWest );

        GridBagLayoutHelper.addComponent( contentPane, splitPane, 0, 1, 3, 2, 1.0, 1.0 );
    }

    private void setupPanelFootprint() {
        panelEast = new JPanel( new GridBagLayout() );
        footprintPanel = new BuildingFootprintPanel( state );
        footprintPanel.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
        footprintPanel.setBackground( Color.white );
        footprintPanel.setPreferredSize( SUBCOMPONENT_DIMENSION );

        panelEast.setMinimumSize( new Dimension( 0, 0 ) );
        GridBagLayoutHelper.addComponent( panelEast, footprintPanel, 0, 0, 1, 1, new Insets( 1, 1, 1, 1 ), CENTER, 1, 1 );
    }

    private void setupOpenGL( boolean testSphere ) {
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered( true );
        caps.setHardwareAccelerated( true );
        caps.setAlphaBits( 8 );
        caps.setAccumAlphaBits( 8 );
        openGLEventListener = new OpenGLEventHandler( testSphere );

        GLCanvas canvas = new GLCanvas( caps );
        canvas.setAutoSwapBufferMode( true );
        canvas.addGLEventListener( openGLEventListener );
        canvas.addMouseListener( openGLEventListener.getTrackBall() );
        canvas.addMouseWheelListener( openGLEventListener.getTrackBall() );
        canvas.addMouseMotionListener( openGLEventListener.getTrackBall() );
        canvas.setPreferredSize( SUBCOMPONENT_DIMENSION );

        GridBagLayoutHelper.addComponent( panelEast, canvas, 0, 1, 1, 1, new Insets( 1, 1, 1, 1 ), CENTER, 1, 1 );
        splitPane.setRightComponent( panelEast );
    }

    /**
     * Adds the actionListener to the visible components to interact with the user.
     * 
     * @param e
     */
    @Override
    public void addListeners( ActionListener e ) {
        editMenuItem.addActionListener( e );
        openShape.addActionListener( e );
        openWMS.addActionListener( e );
        openBuilding.addActionListener( e );
        saveBuilding.addActionListener( e );
        exit.addActionListener( e );
    }

    /**
     * The {@link Scene2DPanel} is a child of this Container
     * 
     */
    @Override
    public Scene2DPanel getScenePanel2D() {
        return scenePanel2D;
    }

    @Override
    public BuildingFootprintPanel getFootprintPanel() {
        return footprintPanel;
    }

    @Override
    public void addHoleWindowListener( ComponentListener c ) {
        resizeComponent.addComponentListener( c );
        panelEast.addComponentListener( c );
        panelWest.addComponentListener( c );
        scenePanel2D.addComponentListener( c );
        splitPane.addComponentListener( c );
    }

    @Override
    public OpenGLEventHandler getOpenGLEventListener() {
        return openGLEventListener;
    }

    /**
     * Sets everything that is needed to handle userinteraction with the checkboxes in the transformationMenu.
     * 
     * @param selectedCheckbox
     *            the checkbox that has been selected by the user.
     */
    @Override
    public void activateTransformationCheckbox( JCheckBox selectedCheckbox ) {
        this.list.getModel().selectThisCheckbox( selectedCheckbox );
        this.menuTransformation.getPopupMenu().setVisible( false );
        this.menuTransformation.setVisible( true );
        this.menuTransformation.setSelected( false );
        if ( state.points != null ) {
            state.points.updateTransformation();
        }
        state.updateDrawingPanels();
        state.conModel.getPanel().updatePoints( state.sceneValues );
        state.conModel.getPanel().repaint();
    }

    @Override
    public JMenu getMenuTransformation() {
        return menuTransformation;
    }

    @Override
    public void addToMenuTransformation( CheckboxListTransformation list ) {
        this.list = list;
        for ( JCheckBox box : list.getModel().getList() ) {
            this.menuTransformation.add( box );
        }

    }

}
