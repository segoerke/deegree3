//$HeadURL$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2012 by:
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
package org.deegree.protocol.wmts.client;

import static org.deegree.protocol.wmts.WMTSConstants.VERSION_100;
import static org.deegree.protocol.wmts.WMTSConstants.WMTS_100_NS;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.deegree.protocol.ows.client.AbstractOWSClient;
import org.deegree.protocol.ows.exception.OWSExceptionReport;
import org.deegree.protocol.ows.http.OwsResponse;
import org.deegree.protocol.wmts.WMTSConstants;

/**
 * API-level client for accessing servers that implement the <a
 * href="http://www.opengeospatial.org/standards/wmts">OpenGIS Web Map Tile Service (WMTS) 1.0.0</a> protocol.
 * 
 * @author <a href="mailto:schneider@occamlabs.de">Markus Schneider</a>
 * @author last edited by: $Author: markus $
 * 
 * @version $Revision: $, $Date: $
 */
public class WMTSClient extends AbstractOWSClient<WMTSCapabilitiesAdapter> {

    public WMTSClient( URL capaUrl ) throws OWSExceptionReport, XMLStreamException, IOException {
        super( capaUrl );
    }

    /**
     * Fetches the specified tile using a <code>GetTile</code> request.
     * 
     * @param layer
     *            layer identifier, must not be <code>null</code>
     * @param style
     *            style identifier, must not be <code>null</code>
     * @param format
     *            output format of the tile, must not be <code>null</code>
     * @param tileMatrixSet
     *            tile matrix set identifier, must not be <code>null</code>
     * @param tileMatrix
     *            tile matrix identifier, must not be <code>null</code>
     * @param tileRow
     *            row index of tile matrix, value between 0 and (matrix height-1)
     * @param tileCol
     *            column index of tile matrix, value between 0 and (matrix width-1)
     * @return server response, never <code>null</code>
     * @throws IOException
     */
    public GetTileResponse getTile( String layer, String style, String format, String tileMatrixSet, String tileMatrix,
                                    int tileRow, int tileCol )
                            throws IOException {

        LinkedHashMap<String, String> kvp = new LinkedHashMap<String, String>();
        kvp.put( "service", "WMTS" );
        kvp.put( "request", "GetTile" );
        kvp.put( "version", VERSION_100.toString() );
        kvp.put( "layer", layer );
        kvp.put( "style", style );
        kvp.put( "format", format );
        kvp.put( "tileMatrixSet", tileMatrixSet );
        kvp.put( "tileMatrix", tileMatrix );
        kvp.put( "tileRow", "" + tileRow );
        kvp.put( "tileCol", "" + tileCol );

        URL endPoint = getGetUrl( WMTSConstants.WMTSRequestType.GetTile.name() );
        OwsResponse response = httpClient.doGet( endPoint, kvp, null );
        return new GetTileResponse( response );
    }

    @Override
    protected WMTSCapabilitiesAdapter getCapabilitiesAdapter( OMElement root, String version )
                            throws IOException {

        QName rootElName = root.getQName();

        if ( !new QName( WMTS_100_NS, "Capabilities" ).equals( rootElName ) ) {
            String msg = "Unexpected WMTS GetCapabilities response element: '" + rootElName + "'.";
            throw new IOException( msg );
        }

        WMTSCapabilitiesAdapter capaAdapter = new WMTSCapabilitiesAdapter();
        capaAdapter.setRootElement( root );
        return capaAdapter;
    }
}