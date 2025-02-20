
readInData <- function(path) {
  data = read.csv(path, sep = ',', header = T)
  return(data)
}

cleanData <- function(dirtyData, round = TRUE, start = 1940, end = 2019) {
  clean.data <- dirtyData
  if(round) {
    clean.data <- dirtyData[which(dirtyData$freq > 0.5),]
    clean.data$freq <- round(clean.data$freq)
  }
  clean.data <- clean.data[which(clean.data$Date < end),]
  clean.data <- clean.data[which(clean.data$Date > start),]
  return(clean.data)
}

cleanDeathData <- function(dirtyData, round = TRUE, start = 1940, end = 2019) {
  return(cleanData(dirtyData, round, start = start, end = end))
}

cleanOBData <- function(dirtyData, largestBirthingAge, round = TRUE) {
  clean.data <- cleanData(dirtyData, round)
  clean.data <- clean.data[which(clean.data$Age >= 15), ]
  clean.data <- clean.data[which(clean.data$Age <= largestBirthingAge), ]
  #clean.data <- clean.data[which(clean.data$CIY == "YES"), ]
  return(clean.data)
}

sourceSummary <- function(data) {
  print(summary(data[which(data$Source == "SIM" ),]))
  print(summary(data[which(data$Source == "STAT"),]))
}

cleanMBData <-function(dirtyData, largestBirthingAge, round = TRUE)  {
  #dirtyData$freq <- ceiling(dirtyData$freq)
  clean.data <- cleanOBData(dirtyData, largestBirthingAge, round)
  clean.data <- clean.data[which(clean.data$NCIY != "0"), ]

  return(clean.data)
}

cleanPartData <- function(dirtyData, round = TRUE, start = 1940, end = 2019) {
  clean.data <- cleanData(dirtyData, round = round, start = start, end = end)
  clean.data <- clean.data[which(clean.data$NPA != "na") , ]
  clean.data$NPA <- droplevels(factor(clean.data$NPA))
  return(clean.data)
}

cleanSepData <- function(dirtyData, round = TRUE) {
  clean.data <- cleanData(dirtyData, round)
  clean.data <- clean.data[which(clean.data$Separated == "YES") , ]
  clean.data$Separated <- droplevels(factor(clean.data$Separated))
  clean.data$NCIP <- droplevels(factor(clean.data$NCIP))
  return(clean.data)
}
