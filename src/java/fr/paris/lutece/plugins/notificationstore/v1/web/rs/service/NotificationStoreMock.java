/*
 * Copyright (c) 2002-2024, City of Paris
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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fr.paris.lutece.plugins.grubusiness.business.customer.Customer;
import fr.paris.lutece.plugins.grubusiness.business.demand.Demand;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandCategory;
import fr.paris.lutece.plugins.grubusiness.business.demand.TemporaryStatus;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandType;
import fr.paris.lutece.plugins.grubusiness.business.notification.MyDashboardNotification;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.DemandDisplay;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.DemandResult;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.NotificationResult;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.plugins.notificationstore.v1.web.service.INotificationStoreTransportProvider;
import fr.paris.lutece.util.ReferenceList;

/**
 * 
 * NotificationStoreTransportRest
 *
 */
public class NotificationStoreMock implements INotificationStoreTransportProvider
{

    /**
     * Logger
     */
    private static Logger _logger = Logger.getLogger( NotificationStoreMock.class );

    /**
     * Simple Constructor
     */
    public NotificationStoreMock( )
    {
    }

    @Override
    public DemandResult getListDemand( String strCustomerId, String strIdDemandType, String strIndex, String strLimitResult, String strNotificationType )
    {
        _logger.debug( "MOCK : Get list of demand for customer id " + strCustomerId );
        return getMockDemandResult( strCustomerId, strIdDemandType, null );
    }

    @Override
    public DemandResult getListOfDemandByStatus( String strCustomerId, String strListStatus, String strIdDemandType, String strIndex, String strLimitResult,
            String strNotificationType, String strCategoryCode )
    {
        _logger.debug( "MOCK : Get list of demand by status for customer id " + strCustomerId );
        return getMockDemandResult( strCustomerId, strIdDemandType, strListStatus );

    }

    @Override
    public NotificationResult getListNotification( String strCustomerId, String strIdDemand, String strIdDemandType, String strNotificationType )
    {
        _logger.debug( "MOCK : Get list of notification of demand id " + strIdDemand );
        return getMockListNotification( strCustomerId, strIdDemand, strIdDemandType );
    }

    @Override
    public List<DemandType> getDemandTypes( )
    {
        _logger.debug( "MOCK: Get list of demand type " );

        List<DemandType> list = new ArrayList<>( );

        DemandType demandType1 = new DemandType( );
        demandType1.setIdDemandType( 123 );
        demandType1.setLabel( "Paris Familles - Inscription scolaire" );
        demandType1.setAppCode( "A63" );
        list.add( demandType1 );

        DemandType demandType2 = new DemandType( );
        demandType2.setIdDemandType( 123 );
        demandType2.setLabel( "demande de subventions" );
        demandType2.setAppCode( "W30" );
        list.add( demandType2 );

        DemandType demandType3 = new DemandType( );
        demandType3.setIdDemandType( 123 );
        demandType3.setLabel( "Formulaires DSIN" );
        demandType3.setAppCode( "F56" );
        list.add( demandType3 );

        return list;
    }

    /**
     * get a list of 3 Mock Demands
     * 
     * @param strCustomerId
     * @param strIdDemandType
     * @param strStatusList
     * @return the demandResult list
     */
    private DemandResult getMockDemandResult( String strCustomerId, String strIdDemandType, String strStatusList )
    {
        DemandResult demandResult = new DemandResult( );
        demandResult.setListDemandDisplay( new ArrayList<DemandDisplay>( ) );

        Customer customer = new Customer( );
        customer.setId( strCustomerId );
        customer.setConnectionId( "AZERTY-AZERTY-AZERTY-AZERTY" );

        for ( int i = 0; i < 3; i++ )
        {

            String status = "EN COURS";
            if ( strStatusList != null && strStatusList.split( "," ).length > 0 )
            {
                status = strStatusList.split( "," ) [0];
            }
            demandResult.getListDemandDisplay( )
                    .add( getOneMockDemandDisplay( i, strIdDemandType != null ? strIdDemandType : "123", 1672531200000L + ( 86400000L * i ), // => 1/1/2023 + i
                                                                                                                                             // days
                            1685836800000L + ( 86400000L * i ), // => 4/6/2023 + i days
                            status, customer ) );
        }

        return demandResult;

    }

    /**
     * get mock list of 3 notifications
     * 
     * @param strCustomerId
     * @param strIdDemand
     * @param strIdDemandType
     * @return the Notification Result list
     */
    private NotificationResult getMockListNotification( String strCustomerId, String strIdDemand, String strIdDemandType )
    {
        NotificationResult notificationResult = new NotificationResult( );

        for ( int i = 0; i < 3; i++ )
        {

            notificationResult.getNotifications( ).add( getOneMockNotification( 1685836800000L + ( 86400000L * i ), // => 4/6/2023 + i days
                    "Mock subject " + i, "Mock message " + i, "EN COURS" ) );
        }

        return notificationResult;
    }

    /**
     * get a mock notification
     *
     * @param date
     * @param subject
     * @param message
     * @param status
     * @return the notification
     */
    private Notification getOneMockNotification( long date, String subject, String message, String status )
    {
        MyDashboardNotification myDashboardNotification = new MyDashboardNotification( );
        myDashboardNotification.setSubject( subject );
        myDashboardNotification.setMessage( message );
        myDashboardNotification.setStatusText( status );

        Notification notification = new Notification( );
        notification.setDate( date );

        notification.setMyDashboardNotification( myDashboardNotification );

        return notification;
    }

    /**
     * get one Mock DemandDisplay
     * 
     * @param id
     * @param demandType
     * @param creationDate
     * @param modifyDate
     * @param status
     * @return the DemandDisplay
     */
    private DemandDisplay getOneMockDemandDisplay( int id, String demandType, long creationDate, long modifyDate, String status, Customer customer )
    {
        Demand demand = new Demand( );
        demand.setUID( id );
        demand.setTypeId( demandType );
        demand.setCreationDate( creationDate );
        demand.setModifyDate( modifyDate );
        demand.setCustomer( customer );

        DemandDisplay demandDisplay = new DemandDisplay( );
        demandDisplay.setDemand( demand );
        demandDisplay.setStatus( status );

        return demandDisplay;
    }

    @Override
    public String deleteNotificationByCuid( String strCustomerId )
    {

        return null;
    }

    @Override
    public List<DemandCategory> getCategoriesList( ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DemandCategory getCategory( int nCategoryId ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DemandCategory createCategory( DemandCategory category ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DemandCategory modifyCategory( DemandCategory category ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCategory( int nCategoryId ) throws NotificationException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<TemporaryStatus> getStatusList( ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporaryStatus getStatus( int nStatusId ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporaryStatus createStatus( TemporaryStatus status ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporaryStatus modifyStatus( TemporaryStatus status ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteStatus( int nStatusId ) throws NotificationException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ReferenceList getGenericStatusList( ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DemandType getDemandType( int nDemandTypeId ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DemandType createDemandType( DemandType demandType ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DemandType modifyDemandType( DemandType demandType ) throws NotificationException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteDemandType( int nDemandTypeId ) throws NotificationException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAllDemandsAndNotifications( String strCustomerId )
    {
        // TODO Auto-generated method stub
        
    }

}
