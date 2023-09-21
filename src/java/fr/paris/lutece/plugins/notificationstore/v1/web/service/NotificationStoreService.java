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
package fr.paris.lutece.plugins.notificationstore.v1.web.service;



import fr.paris.lutece.plugins.grubusiness.business.web.rs.DemandResult;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.NotificationResult;


/**
 * NotificationStoreService
 */
public class NotificationStoreService
{

    /** transport provider */
    private INotificationStoreTransportProvider _transportProvider;

    /**
     * Simple Constructor
     */
    public NotificationStoreService( )
    {
        super( );
    }

    /**
     * Constructor with IIdentityTransportProvider in parameters
     *
     * @param transportProvider
     *            INotificationStoreTransportProvider
     */
    public NotificationStoreService( final INotificationStoreTransportProvider transportProvider )
    {
        super( );
        this._transportProvider = transportProvider;
    }

    /**
     * setter of transportProvider parameter
     *
     * @param transportProvider
     *            INotificationStoreTransportProvider
     */
    public void setTransportProvider( final INotificationStoreTransportProvider transportProvider )
    {
        this._transportProvider = transportProvider;
    }

    
    /**
     * List of demand by customer id, type demand id and index
     * @param strCustomerId
     * @param strIdDemandType
     * @param strIndex (Not required)
     * @param strNotificationType (Not required)
     * @return list of demand
     */
    public DemandResult getListDemand ( String strCustomerId, String strIdDemandType, String strIndex, String strNotificationType )
    {
        return this._transportProvider.getListDemand( strCustomerId, strIdDemandType, strIndex, strNotificationType );
    }
    
    /**
     * List of demand by list of status, customer id, type demand id and index
     * @param strCustomerId
     * @param strListStatus (separated by , )
     * @param strIdDemandType
     * @param strIndex (Not required)
     * @param strNotificationType (Not required)
     * @return list of demand
     */
    public DemandResult getListOfDemandByStatus ( String strCustomerId, String strListStatus, String strIdDemandType, String strIndex, String strNotificationType )
    {
        return this._transportProvider.getListOfDemandByStatus( strCustomerId, strListStatus, strIdDemandType, strIndex, strNotificationType );
    }
    
    /**
     * List of notification by demand id, customer id and type demand id
     * @param strCustomerId
     * @param strIdDemand
     * @param strIdDemandType
     * @return list of notification
     */
    public NotificationResult getListNotification ( String strCustomerId, String strIdDemand, String strIdDemandType )
    {
        return this._transportProvider.getListNotification( strCustomerId, strIdDemand, strIdDemandType );
    }
    
    /**
     * Gets list of demand types
     * 
     * @return list of demand types
     */
    public String getDemandTypes( )
    {
        return this._transportProvider.getDemandTypes( );
    }

}