/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dynjs.runtime.builtins;

import org.dynjs.runtime.AbstractNonConstructorFunction;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.GlobalContext;
import org.dynjs.runtime.Types;

public class DecodeUriComponent extends AbstractNonConstructorFunction {

    public DecodeUriComponent(GlobalContext globalContext) {
        super(globalContext, "uriComonent");
    }

    @Override
    public Object call(ExecutionContext context, Object self, Object... args) {
        String uriComponent = Types.toString( context, args[0] );
        
        String result = URLCodec.decode(context, uriComponent, "" );
        
        return result;
    }
    
    @Override
    public void setFileName() {
        this.filename = "org/dynjs/runtime/builtins/DecodeUriComponent.java";
    }

    @Override
    public void setupDebugContext() {
        this.debugContext = "<native function: decodeURIComponent>";
    }
}
