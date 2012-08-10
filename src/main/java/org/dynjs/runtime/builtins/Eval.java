/**
 *  Copyright 2012 Douglas Campos, and individual contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dynjs.runtime.builtins;

import org.dynjs.api.Function;

public class Eval implements Function {

    @Override
    public Object call(Object self, DynThreadContext context, Object[] arguments) {
        if (arguments.length == 1 && arguments[0] instanceof String) {
            DynJS runtime = context.getRuntime();
            runtime.eval(context, (String) arguments[0]);
        }
        return DynThreadContext.UNDEFINED;
    }

    @Override
    public String[] getParameters() {
        return new String[]{"x"};
    }

}
