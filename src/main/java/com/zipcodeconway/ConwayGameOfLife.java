package com.zipcodeconway;

import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Arrays;

public class ConwayGameOfLife {

    private SimpleWindow displayWindow;
    private int[][] currentMatrix;
    private int[][] nextMatrix;
    private int d;

    public ConwayGameOfLife(Integer dimension) {
        this.d = dimension;
        this.currentMatrix = this.createRandomStart(d);
        this.displayWindow = new SimpleWindow(dimension);
        this.nextMatrix = new int[d][d];
     }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        this.d = dimension;
        this.displayWindow = new SimpleWindow(dimension);
        this.currentMatrix = startmatrix;
        this.nextMatrix = new int[d][d];
    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(50);
        int[][] endingWorld = sim.simulate(50);
    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'

    // Array[OUTER ARRAY][INNER ARRAY]
    private int[][] createRandomStart(Integer dimension) {
        int[][] randStart = new int[dimension][dimension];
        for(int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                randStart[i][j] = (int) Math.round(Math.random());
            }
        }
        return randStart;
    }

    public int[][] simulate(Integer maxGenerations) {

        int count = 1;
        while (count < maxGenerations) {

            this.displayWindow.display(this.currentMatrix, count);

            for (int i = 0; i < this.d; i++) {
                for (int j = 0; j < this.d; j++) {
                    this.nextMatrix[i][j] = this.isAlive(i, j, this.currentMatrix);
                }
            }

            count += 1;
            this.copyAndZeroOut(this.nextMatrix);
            this.displayWindow.sleep(125);
        }

        System.out.print(Arrays.deepToString(this.currentMatrix));

        return this.currentMatrix;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next) {
        this.currentMatrix = next;
        this.nextMatrix = new int[d][d];
    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.

		* ALIVE --> DEAD
		    < 2 live neighbors
		    > 3 live neighbors

		* ALIVE --> ALIVE
		    2 or 3 live neighbors

        * DEAD --> ALIVE
            3 live neighbors
	*/
    private int isAlive(int row, int col, int[][] world) {
        int liveNeighbors = 0;
        int alive = 0;

        // Upper Left Cell
        if (world[(row - 1 + this.d) % this.d][(col - 1 + this.d) % this.d] == 1) {
            liveNeighbors += 1;
        }
        // Directly Above Cell
        if (world[(row - 1 + this.d) % this.d][col] == 1) {
            liveNeighbors += 1;
        }
        // Upper Right Cell
        if (world[(row - 1 + this.d) % this.d][(col + 1 + this.d) % this.d] == 1) {
            liveNeighbors += 1;
        }
        // Left Cell
        if (world[row][(col - 1 + this.d) % this.d] == 1) {
            liveNeighbors += 1;
        }
        // Right Cell
        if (world[row][(col + 1 + this.d) % this.d] == 1) {
            liveNeighbors += 1;
        }
        // Bottom Left Cell
        if (world[(row + 1 + this.d) % this.d ][(col - 1 + this.d) % this.d] == 1) {
            liveNeighbors += 1;
        }
        // Directly Below Cell
        if (world[(row + 1 + this.d) % this.d ][col] == 1) {
            liveNeighbors += 1;
        }
        // Bottom Right Cell
        if (world[(row + 1 + this.d) % this.d ][(col + 1 + this.d) % this.d] == 1) {
            liveNeighbors += 1;
        }

        // ALIVE --> DEAD
        if (world[row][col] == 1 && (liveNeighbors < 2 || liveNeighbors > 3)) {
            alive =  0;
        // ALIVE --> ALIVE
        } else if (world[row][col] == 1 && (liveNeighbors == 2 || liveNeighbors == 3)) {
            alive = 1;
        // DEAD --> ALIVE
        } else if (world[row][col] == 0 && (liveNeighbors == 3)) {
            alive = 1;
        }
        return alive;
    }

}

