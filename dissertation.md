
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

- A thesis project for a prior PHD student
- How complete was the project
    - This project only ran on the students computer
    - Most of the simulation code was functional
    - The R analysis was broken due to out of data packages

## Requirements Specification

- Restating requirements stated at start of year.

## Software Engineering Process

- Mention a more cautious approach in tackling the legacy code, to preserve the existing functionality

- Understanding the code base
    - Identifying how the software operated
    - What worked and what didn't
    - Asking questions to PHD student
    - Documenting code as I read through it
- Executing the program
    - Adjusting parameters and paths to work for my machine
    - Fixing any immediate errors that occur during execution
    - Ensuring the results are correct
- Simplifying execution
    - Supporting execution on any machine
    - Containerisation and virtual environments
    - Reducing the number of necessary dependencies
        - Removing unncessary R packages
        - Removing shell scripts and external scripts
- Simplyifing development
    - Additional tests
        - End to end tests to ensure changes do not lead to inconsitency
    - Refactoring complex sections
- Fixing and integrating features
    - Fixing bugs that occur with specific configurations
    - Integrating features developed but not added to the main program.
- Improving performance
    - Analysis of memory usage using Spark
    - Reducing the memory footprint

## Ethics

- No ethics, attach sheet
- Worth mentioning the existing data is open and free use

## Design

- How the project is designed already
    - Population in simulation
    - R analysis
        - How this is unusual
    - Result recording
    - Emphasis on the complexity of legacy code, and why total refactoring or rewriting is not feasable
- Clarify the design represents how I planned to improve the legacy code
    - Need to extract local references from the project
    - Make it easy executable
        - Virtualising R
        - Consider using Renjin
        - Settling to storing and executing scripts packaged within the jar
    - Make it easily distributable
        - Considered shipping versions with a scripts to run the program
        - Instead reducing the dependencies and shipping the program directly
    - Make the interface simple
    - Add the clear and in-depth documentation

## Implementation

- Details on how I handled the R packages and virtual environment
- How I containerised the project and identified the necessary dependencies
    - How I used and Spark to support distributed factor searching
- How I specifically repaired tests and added e2e tests
    - How the e2e tests ensure my changes do not change the functionality
- How I integrated the R calling and analysis scripts directly into Java
- How I refactored certain sections and why they were necessary
- The specific steps I took to reduce memory usage

## Evaluation

- I underestimated the difficulty to tackling an existing complex project
    - The more I read through the software, the more I saw how complex sections were than what I originally thought
- Recognising the unusual goal of this disseration
    - Did I have a good approach for tackling the legacy code
    - I feel like my engineering process worked well
    - Altough I also felt like I initially spent too much time reading through the source code
        - And that I should tried experimenting more with execution and parameters to understand how it worked from a blackbox view
- Could I have simplified execution through other methods?
    - Considering the time spent and difficulty of containerising
- I felt like I could have added additional features to the simulation
    - However I also felt that there were also too many aspects left unfinished
    - And how those needed to be polished first
- Should I compare my work to other projects?

## Conclusion

## Appendix

### Testing Summary

- Mentioned in my implementation
- Go into specifics of how my tests exactly confirm correctness?

### User Manual

- Link to documentation, or include documentation here

### Git Diff

- Perhaps some light commentary on some of the diffed sections
