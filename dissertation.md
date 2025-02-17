
# Legacy Code

**University of St Andrews**

*Date*

## Abstract

- 250 words

## Declaration

I declare that the material submitted for assessment is my
own work except where credit is explicitly given to others by
citation or acknowledgement. This work was performed
during the current academic year except where otherwise
stated.
The main text of this project report is [insert word count]
words long, including project specification and plan.
Declaration
In submitting this project report to the University of St
Andrews, I give permission for it to be made available for use
in accordance with the regulations of the University Library.
I also give permission for the title and abstract to be
published and for copies of the report to be made and
supplied at cost to any bona fide library or research worker,
and to be made available on the World Wide Web. I retain
the copyright in this work.

## Contents page

## Introduction

## Context Survey

- How complete is the project
- What is missing
- The project designed only to run on the one computer

## Requirements Specification

- Diss priorities

## Software Engineering Process

- First understanding the code base
    - Done by reading and documenting inline through the code
    - Asking questions about the mechanisms developed by the prior student
- Then trying to execute it
    - Adjusting the parameters and paths to work for my computer
    - Fixing the R analysis
    - Checking the results are meaningul
- Then working to make execution easier
    - First using specialised R package renv to keep track of the R package versions used 
    - Then moving the process to a docker container, allowing it to be run on any machine
    - Then reducing the number of dependencies needed
        - Removing unncessary R packages
        - Removing shell scripts and external scripts
- Then working to make development better
    - More tests
    - Refactoring complex sections
- Then fixing bugs with the program
    - Null exceptions in some places
    - still need to fix the death bug
- Then benchmarking and focusing on performance
- And documenting throughout

## Ethics

- No ethics, attach sheet
- Worth mentioning the existing data is open and free use

## Design

- How the project is designed already
    - Population in simulation
    - R analysis
        - How this is unusual
    - Result recording

- Need to extract local references from the project
- Make it easy executable
- Make it easily distributable
- Make the interface simple and well-explained
- Any specific refactor details

## Implementation

## Evaluation

## Conclusion

## Appendix

### Testing Summary

### User Manual

### Git Diff
