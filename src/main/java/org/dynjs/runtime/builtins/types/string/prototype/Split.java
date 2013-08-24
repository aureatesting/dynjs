package org.dynjs.runtime.builtins.types.string.prototype;

import java.util.regex.MatchResult;

import org.dynjs.runtime.AbstractNativeFunction;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.GlobalObject;
import org.dynjs.runtime.JSObject;
import org.dynjs.runtime.PropertyDescriptor;
import org.dynjs.runtime.PropertyDescriptor.Names;
import org.dynjs.runtime.Types;
import org.dynjs.runtime.builtins.types.BuiltinArray;
import org.dynjs.runtime.builtins.types.regexp.DynRegExp;
import org.joni.Region;

public class Split extends AbstractNativeFunction {

    public Split(GlobalObject globalObject) {
        super(globalObject, "separator", "limit");
    }

    @Override
    public Object call(ExecutionContext context, Object self, Object... args) {
        // 15.5.4.12
        Types.checkObjectCoercible(context, self);
        final String s = Types.toString(context, self);

        JSObject a = BuiltinArray.newArray(context);

        long lengthA = 0;

        long lim = (long) (args[1] == Types.UNDEFINED ? Math.pow(2, 32) - 1 : Types.toUint32(context, args[1]));

        int sLen = s.length();

        int p = 0;

        Object r = null;

        if (args[0] instanceof DynRegExp) {
            r = args[0];
        } else {
            r = Types.toString(context, args[0]);
        }

        if (lim == 0) {
            return a;
        }

        if (args[0] == Types.UNDEFINED) {
            a.defineOwnProperty(context, "0", new PropertyDescriptor() {
                {
                    set(Names.VALUE, s);
                    set(Names.WRITABLE, true);
                    set(Names.CONFIGURABLE, true);
                    set(Names.ENUMERABLE, true);
                }
            }, false);
            return a;
        }

        if (sLen == 0) {
            Region z = splitMatch(context, s, 0, r);
            if (z != null) {
                return a;
            }
            a.defineOwnProperty(context, "0", new PropertyDescriptor() {
                {
                    set(Names.VALUE, s);
                    set(Names.WRITABLE, true);
                    set(Names.CONFIGURABLE, true);
                    set(Names.ENUMERABLE, true);
                }
            }, false);
            return a;
        }

        int q = p;

        while (q != sLen) {
            final Region z = splitMatch(context, s, q, r);
            if (z == null) {
                ++q;
            } else {
                int e = z.end[0];
                if (e == p) {
                    ++q;
                } else {
                    final String t = s.substring(p, q);
                    a.defineOwnProperty(context, "" + lengthA, new PropertyDescriptor() {
                        {
                            set(Names.VALUE, t);
                            set(Names.WRITABLE, true);
                            set(Names.CONFIGURABLE, true);
                            set(Names.ENUMERABLE, true);
                        }
                    }, false);
                    ++lengthA;
                    if ( lengthA == lim ) {
                        return a;
                    }
                    p = e;
                    int numCaps = z.beg.length - 1;
                    for ( int i = 0 ; i < numCaps ; ++i ) {
                        final int capNum = i+1;
                        a.defineOwnProperty(context, "" + lengthA, new PropertyDescriptor() {
                            {
                                //set(Names.VALUE, z.group(capNum));
                                set(Names.VALUE, s.substring(z.beg[capNum], z.end[capNum]) );
                                set(Names.WRITABLE, true);
                                set(Names.CONFIGURABLE, true);
                                set(Names.ENUMERABLE, true);
                            }
                        }, false);
                        ++lengthA;
                        if ( lengthA == lim ) {
                            return a;
                        }
                    }
                    q = p;
                }
            }
        }
        final String t = s.substring( p, sLen );
        a.defineOwnProperty(context, "" + lengthA, new PropertyDescriptor() {
            {
                set(Names.VALUE, t );
                set(Names.WRITABLE, true);
                set(Names.CONFIGURABLE, true);
                set(Names.ENUMERABLE, true);
            }
        }, false);
        
        return a;
    }
    
    private Region splitMatch(ExecutionContext context, String s, int q, Object r) {
        if ( r instanceof DynRegExp ) {
            return ((DynRegExp)r).match(s, q);
        }
        
        String rStr = (String) r;
        int rLen = rStr.length();
        int sLen = s.length();
        
        if ( q + rLen > sLen ) {
            return null;
        }
        
        for ( int i = 0 ; i < rLen ; ++i ) {
            if ( s.charAt(q+i) != rStr.charAt( i ) ) {
                return null;
            }
        }
        
        return new Region(0, q+rLen);
    }

}
