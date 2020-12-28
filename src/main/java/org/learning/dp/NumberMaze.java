package org.learning.dp;

/**
 * A number maze is a nn×n grid of positive integers. A token starts in the upper left corner;
 * your goal is to move the token to the lower-right corner. On each turn, you are allowed
 * to move the token up, down,left, or right; the distance you may move the token is determined
 * by the number on its current square.For example, if the token is on a square labeled3,
 * then you may move the token three steps up, three steps down, three steps left, or three steps right.
 *
 * However, you are never allowed to move the token off the edge of the board.Describe and
 * analyze an efficient algorithm that either returns the minimum number of moves required to
 * solve a given number maze, or correctly reports that the maze has no solution
 *
 * {
 *     {3,5,7,4,6},
 *     {5,3,1,5,3},
 *     {2,8,3,1,4},
 *     {4,5,7,2,3},
 *     {3,1,3,2,0},
 * }
 *
 * The above maze will require 8 moves:
 * - [0,0] -> [0,3] -> [4,3] -> [4,1] -> [4,2] -> [1,2] -> [1,1] -> [1,4] -> [4,4]
 *
 */
public class NumberMaze {
}
