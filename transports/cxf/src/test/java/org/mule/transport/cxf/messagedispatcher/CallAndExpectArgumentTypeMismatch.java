/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.cxf.messagedispatcher;

import org.mule.api.MuleContext;

/**
 *
 */
class CallAndExpectArgumentTypeMismatch extends AbstractCallAndExpectIllegalArgumentException
{
    public CallAndExpectArgumentTypeMismatch(String outputEndpointName,
                                             Object payload,
                                             MuleContext muleContext)
    {
        super(outputEndpointName, payload, muleContext);
    }

    @Override
    public String expectedIllegalArgumentExceptionMessage()
    {
        return "argument type mismatch";
    }
}