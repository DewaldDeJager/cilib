/**
 * Computational Intelligence Library (CIlib)
 * Copyright (C) 2003 - 2010
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science
 * University of Pretoria
 * South Africa
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.cilib.measurement.multiple;

import net.sourceforge.cilib.algorithm.Algorithm;
import net.sourceforge.cilib.algorithm.population.PopulationBasedAlgorithm;
import net.sourceforge.cilib.coevolution.competitive.CompetitiveCoevolutionAlgorithm;
import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.measurement.Measurement;
import net.sourceforge.cilib.problem.solution.Fitness;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This class currently only works for PSO, it finds the best personal best fitness
 * values for each population in a multi-population based algorithm without re-calculating
 * the fitness.
 */
public class MultiPopulationFitness implements Measurement<Vector> {

    private static final long serialVersionUID = -608120128187899491L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Measurement getClone() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getValue(Algorithm algorithm) {
        Vector.Builder fitness = Vector.newBuilder();
        CompetitiveCoevolutionAlgorithm ca = (CompetitiveCoevolutionAlgorithm) algorithm;
        for (PopulationBasedAlgorithm currentAlgorithm : ca) {
            Fitness best = null;
            for (Entity e : currentAlgorithm.getTopology()) {
                if (best == null || ((Fitness) e.getProperties().get(EntityType.Particle.BEST_FITNESS)).compareTo(best) > 0) {
                    best = ((Fitness) e.getProperties().get(EntityType.Particle.BEST_FITNESS));
                }
            }
            fitness.add(best.getValue());
        }
        return fitness.build();
    }
}