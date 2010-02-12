/**
 * Copyright (C) 2003 - 2009
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science
 * University of Pretoria
 * South Africa
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package net.sourceforge.cilib.functions.activation;

import net.sourceforge.cilib.controlparameter.ConstantControlParameter;
import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.util.Vectors;

/**
 * The generalised sigmoid function. The function is the general case of the sigmoid function
 * with the ability to specify the steepness of the function as well as an offset that should
 * be taken into consideration.
 */
public class Sigmoid extends ActivationFunction {
    private static final long serialVersionUID = 8291966233976579855L;
    private ControlParameter steepness;
    private ControlParameter offset;

    /**
     * Create a new instance of {@code Sigmoid}. The default instance has the {@code steepness}
     * {@linkplain net.sourceforge.cilib.controlparameter.ControlParameter control parameter} set
     * to a value of {@code 1.0}, with the {@code offset} defined as {@code 0.0}.
     */
    public Sigmoid() {
        setDomain("R(0.0, 1.0)");
        this.steepness = new ConstantControlParameter(1.0);
        this.offset = new ConstantControlParameter(0.0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sigmoid getClone() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Real evaluate(Real input) {
        return new Real(this.evaluate(input.getReal()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(double input) {
        return 1.0 / (1.0 + Math.pow(Math.E, -1.0 * steepness.getParameter() * (input - offset.getParameter())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Real getMaximum() {
        return new Real(1.0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Real getMinimum() {
        return new Real(0.0);
    }

    @Override
    public Vector getGradient(Vector x) {
        return Vectors.create(this.getGradient((Real) x.get(0)).getReal());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getGradient(double number) {
        return number * (1 - number);
    }

    /**
     * Get the {@literal steepness} associated with the {@linkplain Sigmoid}.
     * @return The {@linkplain ControlParameter} representing the {@literal steepness}.
     */
    public ControlParameter getSteepness() {
        return steepness;
    }

    /**
     * Set the {@linkplain ControlParameter} to represent the {@literal steepness} of the function.
     * @param steepness The value to set.
     */
    public void setSteepness(ControlParameter steepness) {
        if (steepness.getParameter() < 0) {
            throw new UnsupportedOperationException("Cannot set steepness to a negative value.");
        }
        this.steepness = steepness;
    }

    /**
     * Get the {@literal offset} associated with the function.
     * @return The {@linkplain ControlParameter} representing the {@literal offset}.
     */
    public ControlParameter getOffset() {
        return offset;
    }

    /**
     * Set the {@linkplain ControlParameter} to represent the {@literal offset} of the function.
     * @param offset The value to set.
     */
    public void setOffset(ControlParameter offset) {
        this.offset = offset;
    }

    /**
     * {@inheritDoc}
     * The active range for sigmoid is -Sqrt(3) - Sqrt(3), and Sqrt(3) = 1.732050808
     */
    @Override
    public double getLowerActiveRange() {
        return -1.732050808;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getUpperActiveRange() {
        return 1.732050808;
    }
}
