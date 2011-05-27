/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.usecases.axis;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.DynamicPortTestCase;
import org.mule.transport.soap.axis.AxisConnector;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO document
 */
public class AxisClientWithComplexTypesTestCase extends DynamicPortTestCase
{
    
    private Trade trade = null;
    private String uri = null;

    @Override
    protected String getConfigResources()
    {
        return "org/mule/test/usecases/routing/axis/axis-client-test.xml";
    }

    @Override
    protected void doSetUp() throws Exception
    {
        trade = new Trade();
        trade.setAccountID(11);
        trade.setCusip("33");
        trade.setCurrency(22);
        trade.setTradeID(22);
        trade.setTransaction(11);
        uri = "axis:http://localhost:" + getPorts().get(0) + "/services/BackOfficeImplBindingImplUMO?method=submitTrade";
    }

    public void testSendComplexDOCLIT() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        Map props = new HashMap();
        props.put(AxisConnector.STYLE, "Document");
        props.put(AxisConnector.USE, "Literal");

        SubmitTrade submittrade = new SubmitTrade();
        submittrade.setArg0(trade);

        // We need to name the parameters weh using Doc/Lit
        // SoapMethod method = new SoapMethod(new QName("submitTrade"),
        // SubmitTradeResponse.class);
        // method.addNamedParameter(new NamedParameter(new QName("submitTrade"),
        // NamedParameter.createQName("Object"), ParameterMode.IN));
        // props.put(MuleProperties.MULE_SOAP_METHOD, method);
        MuleMessage result = client.send(uri, submittrade, props);
        assertNotNull(result);
        SubmitTradeResponse response = (SubmitTradeResponse)result.getPayload();
        assertEquals("RECEIVED", response.get_return().getStatus());

    }

    public void testSendComplexRPCENC() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);

        MuleMessage result = client.send(uri, trade, null);
        assertNotNull(result);
        TradeStatus status = (TradeStatus)result.getPayload();
        assertEquals("RECEIVED", status.getStatus());
    }

    @Override
    protected int getNumPortsToFind()
    {
        return 1;
    }

}