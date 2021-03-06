/*
 * Copyright 2014 JBoss Inc
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
package org.dynjs.ir.instructions;

import java.util.Map;
import org.dynjs.ir.Instruction;
import org.dynjs.ir.Operand;
import org.dynjs.ir.Operation;

/**
 * Return from the current scope with the provided operand (or undefined).
 * This instruction will be added if one is not explicitly present in the
 * underlying program.
 */
public class Return extends Instruction {
    private Operand value;

    public Return(Operand value) {
        super(Operation.RETURN);
        this.value = value;
    }

    public void simplifyOperands(Map<Operand, Operand> renameMap, boolean force) {
        value = value.getSimplifiedOperand(renameMap, force);
    }

    public Operand[] getOperands() {
        return new Operand[] { value };
    }

    public Operand getValue() {
        return value;
    }

    public String toString() {
        return "return " + getValue() + ";";
    }

    public boolean transfersControl() {
        return true;
    }
}
