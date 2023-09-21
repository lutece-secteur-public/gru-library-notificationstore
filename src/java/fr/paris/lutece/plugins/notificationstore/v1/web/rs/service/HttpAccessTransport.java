/*
 * Copyright (c) 2002-2023, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.notificationstore.v1.web.rs.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.log4j.Logger;


import fr.paris.lutece.plugins.notificationstore.v1.web.service.IHttpTransportProvider;
import fr.paris.lutece.util.httpaccess.HttpAccess;

/**
 * IHttpTransportProvider which use library-httpaccess
 */
public class HttpAccessTransport implements IHttpTransportProvider
{
    private static Logger _logger = Logger.getLogger( HttpAccessTransport.class );

    protected HttpAccess _httpClient;
    protected String _strEndPoint;

    public HttpAccessTransport( )
    {
        this._httpClient = new HttpAccess( );
    }

    /**
     * set end point
     * 
     * @param strApiEndPointUrl
     */
    public void setApiEndPointUrl( String strApiEndPointUrl )
    {

        _strEndPoint = strApiEndPointUrl;
    }

    /**
     * get end point
     * 
     * @return strEndPointUrl
     */
    public String getApiEndPointUrl( )
    {

        return _strEndPoint;
    }



    @Override
    public String doGet( String strEndPointUrl, Map<String, String> mapParams, Map<String, String> mapHeadersRequest )
    {
        try
        {
            URIBuilder uriBuilder = new URIBuilder( strEndPointUrl );

            if ( ( mapParams != null ) && !mapParams.isEmpty( ) )
            {
                for ( String strParamKey : mapParams.keySet( ) )
                {
                    uriBuilder.addParameter( strParamKey, mapParams.get( strParamKey ) );
                }
            }

            return this._httpClient.doGet( uriBuilder.toString( ), null, null, mapHeadersRequest );         
        }
        catch( Exception e )
        {
            _logger.error( "LibraryNotificationstore - Error HttpAccessTransport :" + e.getMessage( ), e );
        }

        return StringUtils.EMPTY;
    }

    
    /**
     * add specific authentication to request
     * 
     * @param mapHeadersRequest
     *            map of headers to add
     */
    protected void addAuthentication( Map<String, String> mapHeadersRequest )
    {
        // default : no authentication
    }
}