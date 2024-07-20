# Bellman-Ford Algorithm with Parallelism

## Overview

This project focuses on implementing the Bellman-Ford algorithm using both sequential and parallel approaches. The goal is to understand and utilize parallelism for improving performance using the ForkJoin framework.

## Project Goals

1. **Understand Sequential Code**: Implement the Bellman-Ford algorithm sequentially.
2. **Explore Parallelism**: Use the ForkJoin framework to parallelize the algorithm.
3. **Apply to Real-World Problems**: Solve problems like currency arbitrage using graph algorithms.

## Key Components

### Bellman-Ford Algorithm

- **Purpose**: Compute shortest paths from a single source vertex to all others in a weighted graph.
- **Capability**: Handles negative-weight edges and detects negative cost cycles.

### Applications

- **Currency Arbitrage**: Identify profitable cycles in currency exchange rates.

## Project Structure

### Sequential Implementation

- **Parser**: Convert adjacency matrix to adjacency list.
- **Bellman-Ford Algorithm**: Implemented in `OutSequential.java`.

### Parallel Implementations

1. **Naive Parallelization**: Initial parallel attempt with potential concurrency issues.
   - Implemented in `OutParallelBad.java`.
   
2. **Parallel with Locks**: Fixes concurrency issues using locks.
   - Implemented in `OutParallelLock.java`.
   
3. **Parallel with Incoming Edges**: Optimized using incoming edges without locks.
   - Implemented in `InParallel.java`.

### ForkJoin Tasks

- **ArrayCopyTask**: Handles parallel array copying.
- **RelaxOutTaskBad**: Naive edge relaxation.
- **RelaxOutTaskLock**: Edge relaxation with locking.
- **RelaxInTask**: Edge relaxation using incoming edges.

## Development Environment

- **Java**: Core language for implementation.
- **JUnit**: Testing framework for validation.
- **ForkJoin Framework**: Used for parallelism.
