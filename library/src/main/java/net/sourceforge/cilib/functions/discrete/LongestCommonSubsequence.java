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
package net.sourceforge.cilib.functions.discrete;

import net.sourceforge.cilib.algorithm.AbstractAlgorithm;
import net.sourceforge.cilib.algorithm.population.PopulationBasedAlgorithm;
import net.sourceforge.cilib.functions.DiscreteFunction;
import net.sourceforge.cilib.problem.Problem;
import net.sourceforge.cilib.problem.dataset.TextDataSetBuilder;
import net.sourceforge.cilib.type.types.Numeric;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * Implementation of the Longest Common Subsequence problem.
 *
 */
public class LongestCommonSubsequence implements DiscreteFunction {

    private static final long serialVersionUID = -3586259608521073084L;

    @Override
    public Integer apply(Vector input) {
        int v = 0;
        int l = length(input);
        int m = matches(input);
        int k = this.getDataSetSize();

        v = l + (30 * m);

        if (l == getShortestString().length()) {
            v += 50;
        }

        if (m == k) {
            v *= 3000;
        } else {
            v *= -1000 * (k - m);
        }

        return v;
    }

    /**
     * Returns the lengh of the shortest string or the length of the first
     * string
     *
     * @return The shortest length
     */
    private String getShortestString() {
        PopulationBasedAlgorithm popAlgorithm = (PopulationBasedAlgorithm) AbstractAlgorithm.get();
        Problem problem = popAlgorithm.getOptimisationProblem();
        TextDataSetBuilder dataSetBuilder = (TextDataSetBuilder) problem.getDataSetBuilder();

        return dataSetBuilder.getShortestString();
    }

    private int getDataSetSize() {
        PopulationBasedAlgorithm popAlgorithm = (PopulationBasedAlgorithm) AbstractAlgorithm.get();
        Problem problem = popAlgorithm.getOptimisationProblem();
        TextDataSetBuilder dataSetBuilder = (TextDataSetBuilder) problem.getDataSetBuilder();

        return dataSetBuilder.size();
    }

    /**
     *
     * @param x
     * @return
     */
    private int length(Vector x) {
        int count = 0;

        for (Numeric n : x) {
            if (n.booleanValue()) {
                count++;
            }
        }

        return count;
    }

    private int matches(Vector x) {
        PopulationBasedAlgorithm popAlgorithm = (PopulationBasedAlgorithm) AbstractAlgorithm.get();
        Problem problem = popAlgorithm.getOptimisationProblem();
        TextDataSetBuilder dataSetBuilder = (TextDataSetBuilder) problem.getDataSetBuilder();

        int count = 0;

        String targetSubSequence = this.getSubSequence(x, this.getShortestString());

        for (int i = 0; i < dataSetBuilder.size(); i++) {
            String tmp = this.getSubSequence(x, dataSetBuilder.get(i));

            if (tmp.equals(targetSubSequence)) {
                count++;
            }
        }

        return count;
    }

    private String getSubSequence(Vector x, String target) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < x.size(); i++) {
            Numeric n = x.get(i);
            if (n.booleanValue()) {
                builder.append(target.charAt(i));
            }
        }

        return builder.toString();
    }
}