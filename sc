#AKALIRIS
CMEANS

library(clv)
data(iris)
data <- iris[,1:4]

p<-function(k){
  # cluster data
  agnes.mod <- agnes(data) # create cluster tree
  v.pred <- as.integer(cutree(agnes.mod,k)) # "cut" the tree
 
  intraclust = "centroid"
  interclust = "centroid"
 
  # compute Dunn indicies (also Davies-Bouldin indicies)
  # 1. optimal solution:
 
  # compute intercluster distances and intracluster diameters
  cls.scatt <- cls.scatt.data(data, v.pred, dist="manhattan")
  return(v.pred)
}

# once computed valuse use in both functions
dunn1 <- clv.Dunn(cls.scatt, intraclust, interclust)
davies1 <- clv.Davies.Bouldin(cls.scatt, intraclust, interclust)

# 2. functional solution:

# define new Dunn and Davies.Bouldin functions
Dunn <- function(data,clust)
  clv.Dunn( cls.scatt.data(data,clust),
            intracls = "centroid",
            intercls = "centroid"
  )
Davies.Bouldin <- function(data,clust)
  clv.Davies.Bouldin( cls.scatt.data(data,clust),
                      intracls = "centroid",
                      intercls = "centroid"
  )

# compute indicies
DIfnc<-function(k){
  dunn2 <- Dunn(data, p(k))
}
DBfnc<-function(k){
  davies2 <- Davies.Bouldin(data, p(k))
}

DI<-c()
DB<-c()

for(i in 2:10){
  DI<-c(DI,DIfnc(i))
}

for(i in 2:10){
  DB<-c(DB,DBfnc(i))
}

plot(c(2:10),DB,type="o")
plot(c(2:10),DI,type="o")

CMEANS

#check plot
#check for Iris
par(mfrow=c(2,2))
library(philentropy)
it=1
eps=0.1
m=2
no_of_clusters=3
no_of_dimensions=4
no_of_datapoints=150

datapoints<-c()
#for(i in 1:no_of_dimensions){
#  datapoints<-cbind(datapoints,round(runif(no_of_datapoints,min=1,max=10)))
#}
data(iris)
datapoints=cbind(iris[,1],iris[,2],iris[,3],iris[,4])

#step1
clusters<-c()
for(i in 1:no_of_clusters){
  clusters<-cbind(clusters,runif(no_of_datapoints))
}
for(i in 1:nrow(clusters)){
  clusters[i,]=clusters[i,]/sum(clusters[i,])
}
clusters

#datapoints<-cbind(c(1,2,3,4,5,6),c(6,5,8,4,7,9))
#clusters<-cbind(c(0.8,0.9,0.7,0.3,0.5,0.2),c(0.2,0.1,0.3,0.7,0.5,0.8))
centroid<-c()

repeat{
 
  #step2
  prev_centroid=centroid
  centroid<-c()
  for(i in 1:no_of_clusters){
    temp<-c()
    for(j in 1:no_of_dimensions){
      temp<-c(temp,sum((clusters[,i]^m)*datapoints[,j])/sum(clusters[,i]^m))
    }
    centroid<-rbind(centroid,temp)
  }
  rownames(centroid)<-c()
  centroid
 
  #step3
  dissimilarity<-c()
  for(i in 1:no_of_clusters){
    temp<-c()
    for(j in 1:no_of_datapoints){
      temp<-rbind(temp,distance(rbind(centroid[i,],datapoints[j,]),method="euclidean"))
    }
    dissimilarity<-cbind(dissimilarity,temp)
  }
  colnames(dissimilarity)<-c()
  dissimilarity
 
  #step4
  for(i in 1:nrow(dissimilarity)){
    dissimilarity[i,]=((1/dissimilarity[i,])/sum(1/dissimilarity[i,]))
  }
  dissimilarity
 
  cat('Iteration',it,'\n')
  print(cbind(datapoints,clusters,dissimilarity))
  #plot(1:150,rowMax(dissimilarity))
  #######
  k<-c()
  for(i in 1:no_of_datapoints){
    k<-c(k,which(dissimilarity[i,]==max(dissimilarity[i,])))
  }
  clusplot(datapoints, k, color=TRUE, shade=TRUE,
           labels=no_of_clusters, lines=0)
  ########
  clusters=dissimilarity
 
  #exit condition
  it=it+1
  if(it>=3){
    flag=0
    for(i in 1:no_of_clusters){
      if(distance(rbind(centroid[i,],prev_centroid[i,]),method="euclidean")<eps){
        flag=flag+1
      }
    }
    if(flag==no_of_clusters){
      break
    }
  }
}


#AKALCMEANS
library(philentropy)
it=1
eps=1.9
m=2
no_of_clusters=2
no_of_dimensions=2
no_of_datapoints=6

datapoints<-c()
for(i in 1:no_of_dimensions){
  datapoints<-cbind(datapoints,round(runif(no_of_datapoints,min=1,max=10)))
}
datapoints

#step1
clusters<-c()
for(i in 1:no_of_clusters){
  clusters<-cbind(clusters,runif(no_of_datapoints))
}
for(i in 1:nrow(clusters)){
  clusters[i,]=clusters[i,]/sum(clusters[i,])
}
clusters

#datapoints<-cbind(c(1,2,3,4,5,6),c(6,5,8,4,7,9))
#clusters<-cbind(c(0.8,0.9,0.7,0.3,0.5,0.2),c(0.2,0.1,0.3,0.7,0.5,0.8))
centroid=c()
repeat{
  
  #step2
  prev_centroid=centroid
  centroid<-c()
  for(i in 1:no_of_clusters){
    temp<-c()
    for(j in 1:no_of_dimensions){
      temp<-c(temp,sum((clusters[,i]^m)*datapoints[,j])/sum(clusters[,i]^m))
    }
    centroid<-rbind(centroid,temp)
  }
  rownames(centroid)<-c()
  centroid
  
  #step3
  dissimilarity<-c()
  for(i in 1:no_of_clusters){
    temp<-c()
    for(j in 1:no_of_datapoints){
      temp<-rbind(temp,distance(rbind(centroid[i,],datapoints[j,]),method="euclidean"))
    }
    dissimilarity<-cbind(dissimilarity,temp)
  }
  colnames(dissimilarity)<-c()
  dissimilarity
  
  #step4
  for(i in 1:nrow(dissimilarity)){
    dissimilarity[i,]=(1/(dissimilarity[i,])/(sum(1/dissimilarity[i,])))
  }
  dissimilarity
  
  cat('Iteration',it,'\n')
  print(cbind(datapoints,clusters,dissimilarity))
  clusters=dissimilarity
  
  #exit condition
  it=it+1
  if(it==3){
    flag=0
    for(i in 1:no_of_clusters){
      if(distance(rbind(centroid[i,],prev_centroid[i,]),method="euclidean")<eps){
        flag=flag+1
      }
    }
    if(flag==no_of_clusters){
      break
    }
  }
}

#POOJACMEANS
library(clv)
m<-2
data <- read.csv("iris.csv")
points<-nrow(data)
dimensions<-ncol(data)-1
membershipMatrix<-matrix(nrow=points,ncol=dimensions)
optimalCluster<-function(){
  dunn1<-vector()
  db1<-vector()
  for(i in 2:10){
    agnesMod<-agnes(data[-ncol(data)])
    vPred<-as.integer(cutree(agnesMod,i))
    clsScatter<-cls.scatt.data(data[-ncol(data)],vPred,"euclidean")
    dunn1<-append(dunn1,clv.Dunn(clsScatter,"centroid","centroid"))
    db1<-append(db1,clv.Davies.Bouldin(clsScatter,"centroid","centroid"))
  }
  plot(c(2:10),dunn1,xlab="No of clusters",ylab="Dunn Index",type="o",main="Dunn Index Evaluation")
  plot(c(2:10),db1,xlab="No of clusters",ylab="Db Index",type="o",main="Db Index Evaluation")
  
}
normalize <- function(x) {
  return ((x - min(x)) / (max(x) - min(x)))
}
distance<-function(centroids,clusters){
  distanceMatrix<-matrix(nrow=points,ncol=clusters)
  for(i in 1:points){
    for(j in 1:clusters){
      dist <- sqrt( sum( ( data[i,-ncol(data)] - centroids[j,] )^2 ) )
      distanceMatrix[i,j]<-dist
    }
  } 
  return(distanceMatrix)
}
membership<-function(distanceMatrix,clusters){
  
  membershipMatrix<-matrix(0,nrow=points,ncol=clusters)
  for(i in 1:nrow(distanceMatrix)){
    for(j in 1:ncol(distanceMatrix)){
      up=(1/distanceMatrix[i,j])^(1/(m-1))
      down=0
      for(x in 1:ncol(distanceMatrix)){
        down=down+(1/distanceMatrix[i,x])
      }
      down1=down^(1/(m-1))
      membershipMatrix[i,j]<-up/down1
      if(is.nan(membershipMatrix[i,j])){
        membershipMatrix[i,j]=0;
      }
      
    }
  }
  return(membershipMatrix)
}
calculateCentroid<-function(membershipMatrix,clusters){
  centroid<-matrix(nrow=clusters,ncol=dimensions)
  for(i in 1:nrow(centroid)){
    for(j in 1:ncol(centroid)){
      centroid[i,j]<-( sum(data[,j] * membershipMatrix[,i]^m)/sum(membershipMatrix[,i]^m) )
      centroid[i,j]<-round(centroid[i,j],3)
    }
  }
  return(centroid)
}
##for(i in 1:(ncol(data)-1)){
##  data[,i]=normalize(data[,i])
##}
cMeans<-function(clusters){
  prevCentroids=matrix(nrow=clusters,ncol=dimensions)
  for(i in 1:nrow(prevCentroids)){
    for(j in 1:ncol(prevCentroids))
      prevCentroids[i,j]<-round(runif(1,min(data[,j]),max(data[,j])),4)
  }
  centroids=prevCentroids
  flag<-TRUE
  while(flag){
    distanceMatrix<-distance(centroids,clusters)
    memberShipMatrix<-membership(distanceMatrix,clusters)
    centroids<-calculateCentroid(memberShipMatrix,clusters)
    if(identical(prevCentroids,centroids)){
      flag=FALSE
    }
    else{
      prevCentroids=centroids
    }
    i=i+1
  }
  print("FINAL CENTROIDS")
  print(centroids)
  clusterOutput<-vector()
  for(i in 1:nrow(memberShipMatrix)){
    clusterOutput=append(clusterOutput,which.max(memberShipMatrix[i,]))
  }
  plot(iris[c(1, 2)], col = data[,ncol(data)],main="Original clustered output")
  plot(iris[c(1, 2)], col = clusterOutput,main="CMeans clustered output")
  points(centroids[,c(1, 2)], col = 1:length(unique(clusterOutput)), pch = 8, cex = 2)
}
par(mfrow=c(2,2))
optimalCluster()
cMeans(3)

#SUGENO
library(frbs)
normalize <- function(x,new_min,new_max) {
  return (((x - min(x)) / (max(x) - min(x)))*(new_max-new_min)+new_min)
}
varinp.mf <- matrix(c(1, 1000, 1200, 1400, NA, 1, 1400, 1600, 1800, NA,
                      1, 2.5, 5, 7.5, NA, 1, 7.5, 10, 14, NA),                  
                    nrow = 5, byrow = FALSE)

num.fvalinput <- matrix(c(2, 2), nrow=1)
varinput.1 <- c("smallW", "largeW")
varinput.2 <- c("smallT", "largeT")
names.varinput <- c(varinput.1, varinput.2)
range.data <- matrix(c(1000,2000, 0, 15,1000,15000), nrow=2)
type.defuz <- "WAM"
type.tnorm <- "MIN"
type.snorm <- "MAX"
type.implication.func <- "ZADEH"
name <- "Sim-0"
newdata<- read.csv("tsk.csv")
colnames.var <- c("W", "T","AU")
type.model <- "TSK"
num.fvaloutput <- matrix(c(2), nrow=1)
varoutput.1 <- c("small","large")
names.varoutput <- c(varoutput.1)
varout.mf <- matrix(c(1, 2000, 6000, 10000,NA ,
                      1, 10000, 12500, 15000, NA
),
nrow = 5, byrow = FALSE)
func.tsk <- matrix(c(4.6925, -526.2, 2631, 3.4765, -210.5, 2103, 4.6925, -526.2, 2631),
                   nrow = 3, byrow = TRUE)
rule <- matrix(c("largeW", "and", "smallT", "->",
                 "smallW", "or", "largeT", "->",
                 "smallW", "and", "smallT", "->"),
               nrow = 3, byrow = TRUE)
object <- frbs.gen(range.data, num.fvalinput, names.varinput,
                   num.fvaloutput = NULL, varout.mf = NULL, names.varoutput = NULL, rule,
                   varinp.mf, type.model, type.defuz = NULL, type.tnorm, type.snorm,
                   func.tsk, colnames.var, type.implication.func, name)
par(mfrow=c(2,2))
plotMF(object)
#for(i in 1:(ncol(data)-1)){
# data[,i]=normalize(data[,i],0,1)
#}
res <- predict(object, newdata[,-ncol(newdata)])$predicted.val

label<-vector()
labelEncoding<-vector()
for(i in 1:length(res)){
  for(j in 1:ncol(varout.mf)){
    lowerbound=varout.mf[2,j]
    upperbound=varout.mf[4,j]
    if(res[i]<=upperbound && res[i]>lowerbound ){
      label=append(label,varoutput.1[j])
      labelEncoding=append(labelEncoding,j)
    }
  }
}
print("Defuzzified values ")
print(res)
print("Associated Class")
print(label)
output<-factor(label)
expectedOutput<-factor(newdata[,ncol(newdata)])
levels(expectedOutput)<-varoutput.1
levels(output)<-varoutput.1
cm=confusionMatrix(expectedOutput,output)
print("Confusion Matrix")
print(cm)
cat("Accuracy",cm$overall["Accuracy"],"\n")
if(is.na(cm$byClass['Pos Pred Value'])){
  cat("Precision",0,"\n" )
}else{
  cat("Precision",cm$byClass['Pos Pred Value'],"\n" )
}
if(is.na(cm$byClass['Sensitivity'])){
  cat("Recall",0,"\n")
}else{
  cat("Recall",cm$byClass['Sensitivity'],"\n")
}
if(is.na(cm$byClass['Pos Pred Value'])||is.na(cm$byClass['Sensitivity'])){
  cat("F Measure",0,"\n")
}else{
  Precision=cm$byClass['Pos Pred Value']
  Recall=cm$byClass['Sensitivity']
  Fmeasure=(2 * Precision * Recall) / (Precision + Recall)
  cat("F Measure",Fmeasure,"\n")
}
Sys.sleep(10)
plot(newdata[,1],newdata[,2],xlab="W",ylab="T",col=labelEncoding,main="W vs T",pch=1)
legend("bottomright",
       legend = varoutput.1, col=1:length(varoutput.1),pch=1,inset=c(0,0))

#MAMDANI
library(frbs)
library(caret)
normalize <- function(x,new_min,new_max) {
  return (((x - min(x)) / (max(x) - min(x)))*(new_max-new_min)+new_min)
}
varinp.mf <- matrix(c(1, 0, 0, 30, NA, 1, 30, 45, 60, NA, 1, 60, 100, 100,NA,
                      1, 0, 0, 30, NA, 1, 30, 45, 60, NA, 1, 60, 100, 100,NA),                  
                    nrow = 5, byrow = FALSE)

num.fvalinput <- matrix(c(3, 3), nrow=1)
varinput.1 <- c("small", "medium_dirtiness", "large")
varinput.2 <- c("not_greasy", "medium_type","greasy")
names.varinput <- c(varinput.1, varinput.2)
range.data <- matrix(c(0,100, 0, 100,0,100), nrow=2)
type.defuz <- "COG"
type.tnorm <- "MIN"
type.snorm <- "MAX"
type.implication.func <- "ZADEH"
name <- "Sim-0"
#newdata<- matrix(c(30, 75,  45, 80, 30, 45), nrow= 3, byrow = TRUE)
newdata<-read.csv("dirt.csv")
colnames.var <- c("dirtiness", "type of dirt", "washing time")
num.fvaloutput <- matrix(c(5), nrow=1)
varoutput.1 <- c("very_short", "short", "medium","long","very_long")
names.varoutput <- c(varoutput.1)
varout.mf <- matrix(c(1, 0, 10, 20,NA ,
                      1, 20, 30, 40, NA,
                      1, 40, 50, 60, NA,
                      1,60,70,80,NA,
                      1,80,90,100,NA),
                    nrow = 5, byrow = FALSE)
type.model <- "MAMDANI"
rule <- matrix(c("large","and","greasy","->","very_long",
                 "medium_dirtiness","and","greasy","->","long",
                 "small","and","greasy","->","long",
                 "large","and","medium_type","->","long",
                 "medium_dirtiness","and","medium_type","->","medium",
                 "small","and","medium_type","->","medium",
                 "large","and","not_greasy","->","medium",
                 "medium_dirtiness","and","not_greasy","->","short",
                 "small","and","not_greasy","->","very_short"), 
               nrow=9, byrow=TRUE) 
object <- frbs.gen(range.data, num.fvalinput, names.varinput, num.fvaloutput, varout.mf, 
                   names.varoutput, rule, varinp.mf, type.model, type.defuz, type.tnorm, 
                   type.snorm, func.tsk = NULL, colnames.var, type.implication.func, name)
par(mar=c(1,1,1,1))
summary(object)
plotMF(object)
#for(i in 1:(ncol(data)-1)){
# data[,i]=normalize(data[,i],0,1)
#}
res <- predict(object, newdata[,-ncol(newdata)])$predicted.val
print("Defuzzified values")
print(res)
label<-vector()
labelEncoding<-vector()
for(i in 1:length(res)){
  for(j in 1:ncol(varout.mf)){
    lowerbound=varout.mf[2,j]
    upperbound=varout.mf[4,j]
    if(res[i]<=upperbound && res[i]>lowerbound ){
      label=append(label,varoutput.1[j])
      labelEncoding=append(labelEncoding,j)
    }
  }
}
print("Associated Classes")
print(label)
output<-factor(label)
expectedOutput<-factor(newdata[,ncol(newdata)])
levels(expectedOutput)<-varoutput.1
levels(output)<-varoutput.1
print(cm)
cat("Accuracy",cm$overall["Accuracy"],"\n")
if(is.na(cm$byClass['Pos Pred Value'])){
  cat("Precision",0,"\n" )
}else{
  cat("Precision",cm$byClass['Pos Pred Value'],"\n" )
}
if(is.na(cm$byClass['Sensitivity'])){
  cat("Recall",0,"\n")
}else{
  cat("Recall",cm$byClass['Sensitivity'],"\n")
}
if(is.na(cm$byClass['Pos Pred Value'])||is.na(cm$byClass['Sensitivity'])){
  cat("F Measure",0,"\n")
}else{
  
  Precision=cm$byClass['Pos Pred Value']
  Recall=cm$byClass['Sensitivity']
  Fmeasure=(2 * Precision * Recall) / (Precision + Recall)
  cat("F Measure",Fmeasure,"\n")
  
}
Sys.sleep(10)
par(mfrow=c(1,1))
plot(newdata[,1],newdata[,2],xlab="dirtiness",ylab="Type of dirt",col=labelEncoding,main="Dirtiness vs DirtType",pch=2)
legend("topright",
       legend = varoutput.1, col=1:length(varoutput.1),pch=2,inset=c(0,0.35))

#COSINE
data=read.csv("iris.csv")
normalize <- function(x,new_min,new_max) {
  return (((x - min(x)) / (max(x) - min(x)))*(new_max-new_min)+new_min)
}
makeSymm <- function(m) {
  m[lower.tri(m)] <- t(m)[lower.tri(m)]
  return(m)
}
composition <- function(r1,r2) {
  result = matrix(0,nrow(r1),ncol(r1))
  for(i in c(1:nrow(r1))) {
    for(j in c(1:ncol(r1))) {
      result[i,j] = max(pmin(r1[i,],r2[,j]))
    }
  }
  return(result)
}
checkTransitivity<-function(fuzzyMatrix){
  rows=nrow(fuzzyMatrix)
  cols=ncol(fuzzyMatrix)
  for(i in 1:(rows-1)){
    for(j in (i+1):rows){
      value1=fuzzyMatrix[i,j]
      for(x in 1:cols){
        value2=fuzzyMatrix[j,x]
        value3=fuzzyMatrix[i,x]
        if(value3<min(value1,value2)){
          return(FALSE)
        }
      }
    }
  }
  return(TRUE)
  
}
minMax<-function(){
  fuzzyMatrix=matrix(1,nrow(data),nrow(data))
  rows=nrow(fuzzyMatrix)
  for(i in 1:(rows-1)){
    for(j in (i+1):rows){
      up=0
      down=0
      for(x in 1:(ncol(data)-1)){
        up=up+min(data[i,x],data[j,x])
        down=down+max(data[i,x],data[j,x])
      }
      fuzzyMatrix[i,j]<-round(up/down,3)
      
    }
  }
  return(makeSymm(fuzzyMatrix))
}
cosineAmplitude<-function(){
  fuzzyMatrix=matrix(1,nrow(data),nrow(data))
  rows=nrow(fuzzyMatrix)
  for(i in 1:(rows-1)){
    for(j in (i+1):rows){
      up=0
      downFirst=0
      downSecond=0
      for(x in 1:(ncol(data)-1)){
        up=up+(data[i,x]*data[j,x])
        downFirst=downFirst+(data[i,x]^2)
        downSecond=downSecond+(data[j,x]^2)
      }
      down=sqrt(downFirst*downSecond)
      fuzzyMatrix[i,j]<-round(up/down,4)
      
    }
  }
  return(makeSymm(fuzzyMatrix))
}
lambdaCut<-function(fuzzyMatrix,cut){
  l=matrix(0,nrow(fuzzyMatrix),ncol(fuzzyMatrix))
  for(i in 1:nrow(fuzzyMatrix)){
    for(j in 1:ncol(fuzzyMatrix)){
      if(fuzzyMatrix[i,j]>=cut){
        l[i,j]=1
      }
    }
  }
  return(l)
}
for(i in 1:(ncol(data)-1)){
  data[,i]=normalize(data[,i],0,1)
}
print(data)
fm=minMax()
flag=TRUE
result=fm
while(flag){
  tm=checkTransitivity(result)
  if(tm==TRUE){
    flag=FALSE
  }
  else{
    result=composition(result,fm)
  }
}
print(result)
cutMatrix=lambdaCut(result,0.75)
groups<-list()
output<-rep(0,nrow(data))
print('GROUPS')
for(i in c(1: ncol(cutMatrix))) {
  position = which(cutMatrix[,i] == 1)
  if(length(position) > 0)
    groups[[length(groups)+1]]<-position
  label<-length(groups)
  for(j in 1:length(position)){
    output[position[j]]<-label
  }
  cutMatrix[position,] = 0;
}
x<-vector()
labels<-vector()
for(i in 1:length(groups)){
  x=append(x,length(groups[[i]]))
  labels=append(labels,paste("group",i))
}
print(groups)

expectedOutput<-as.numeric(data[,ncol(data)])
y1<-factor(output)
y2<-factor(expectedOutput)
levels(y2)<-c(1:length(groups))
levels(y1)<-c(1:length(groups))
print("Confusion Matrix")
cm=confusionMatrix(y2,y1)
print(cm)
cat("Accuracy",cm$overall["Accuracy"],"\n")
if(is.na(cm$byClass['Pos Pred Value'])){
  cat("Precision",0,"\n" )
}else{
  cat("Precision",cm$byClass['Pos Pred Value'],"\n" )
}
if(is.na(cm$byClass['Sensitivity'])){
  cat("Recall",0,"\n")
}else{
  cat("Recall",cm$byClass['Sensitivity'],"\n")
}
if(is.na(cm$byClass['Pos Pred Value'])||is.na(cm$byClass['Sensitivity'])){
  cat("F Measure",0,"\n")
}else{
  Precision=cm$byClass['Pos Pred Value']
  Recall=cm$byClass['Sensitivity']
  Fmeasure=(2 * Precision * Recall) / (Precision + Recall)
  cat("F Measure",Fmeasure,"\n")
}
par(mfrow=c(1,2))
pie(x,labels,main="Count of records in each Class",col=rainbow(length(x)))
plot(data[,1],data[,2],xlab="Sepal Length",ylab="Sepal Width",col=output,main="Sepal Length vs SepalWidth",pch=2)
legend("topright",
       legend = labels, col=1:length(labels),pch=2,inset=c(0,0))

#AKALCOSINE
LAMBDA
matrix<-cbind(c(1,0.8,0,0.1,0.2),
              c(0.8,1,0.4,0,0.9),
              c(0,0.4,1,0,0),
              c(0.1,0,0,1,0.5),
              c(0.2,0.9,0,0.5,1))
lambda<-0.6
matrix[matrix<lambda] <- 0
matrix[matrix>=lambda] <- 1
print(matrix)

EQUIVALENCE RELATION
is_reflexive <<- function(mat){
  for(i in 1:length(mat)){
    if(mat[[i]][[i]] != 1)
      return(FALSE)
  }
  return(TRUE)
}

is_symmetric <<- function(mat){
  for(i in 1:(length(mat)-1)){
    for(j in c((i+1):length(mat))){
      if(mat[[i]][[j]] != mat[[j]][[i]])
        return(FALSE)
    }
  }
  return(TRUE)
}

do_lambdacut <<- function(mat, lambda){
  for(i in 1:length(mat)){
    for(j in 1:length(mat)){
      if(mat[[i]][[j]] > lambda)
        mat[[i]][[j]] = 1
      else
        mat[[i]][[j]] = 0
    }
  }
  return(mat)
}


is_transitive <<- function(mat){
  for(i in 1:(length(mat)-2)){
    for(j in c((i+1):(length(mat)-1))){
      for(k in c((j+1):length(mat))){
        if(min(mat[[i]][[j]],mat[[j]][[k]]) > mat[[i]][[k]])
          return(FALSE)
      }
    }
  }
  return(TRUE)
}

do_composition <<- function(mat){
  mat_2 = mat
  for(i in 1:(length(mat))){
    for(k in c(1:length(mat))){
      temp_list = list()
      for(j in c(1:length(mat))){
        temp_list[length(temp_list)+1] = min( mat[[i]][[j]] , mat[[j]][[k]] )
      }
      mat_2[[i]][[k]] = max(unlist(temp_list))
    }
  }
  return(mat_2)
}

get_class <<- function(mat){
  final_list = list()
  for(i in 1:length(mat)){
    temp_list = c()
    for(j in 1:length(mat)){
      if(mat[[i]][[j]] == 1)
        temp_list[length(temp_list)+1] = j
    }
    final_list[[length(final_list) + 1]] = temp_list
  }
  return(final_list)
}

# mat = list(c(1,0.8,0,0.1,0.2),
#            c(0.8,1,0.4,0,0.9),
#            c(0,0.4,1,0,0),
#            c(0.1,0,0,1,0.5),
#            c(0.2,0.9,0,0.5,1))

mat = list(c(1,0.836,0.913,0.683,0.981),
           c(0.836,1,0.934,0.390,0.745),
           c(0.913,0.934,1,0.44,0.818),
           c(0.683,0.390,0.44,1,0.754),
           c(0.981,0.745,0.818,0.754,1))

# mat = list(c(0.3,0.4),
#            c(0.4,0.3))

is_reflexive(mat)
is_symmetric(mat)
count = 0
while(!is_transitive(mat)){
  mat = do_composition(mat)
  count = count + 1
}

print(count)
print(mat)
mat = do_lambdacut(mat, 0.80)
print(mat)
print(get_class(mat))

COSINE AMPLITUDE

mat = list(c(0.3,0.6,0.1),
           c(0.2,0.4,0.4),
           c(0.1,0.6,0.3),
           c(0.7,0.2,0.1),
           c(0.6,0.4,0))

get_cosine_relation  <<- function(l1,l2){
  num = 0
  dnm1 = 0
  dnm2 = 0
  for(i in 1:(length(l1))){
    print(l1[i])
    num = num + l1[i] * l2[i]
    dnm1 =  dnm1 + l1[i] * l1[i]
    dnm2 =  dnm2 + l2[i] * l2[i]
  }
  return(num/(sqrt(dnm1*dnm2)))
}

get_cosine_relation_mat <<- function(mat){
  rel_mat = mat
  for(i in 1:(length(mat)-1)){
    rel_mat[[i]][i] = 1
    for(j in c((i+1):length(mat))){
      val = get_cosine_relation(mat[[i]],mat[[j]])
      rel_mat[[i]][j] = val
      rel_mat[[j]][i] = val
    }
  }
  rel_mat[[length(mat)]][length(mat)] = 1
  return(rel_mat)
}

print(get_cosine_relation_mat(mat))

MAX MIN
max_min_relation  <<- function(l1,l2){
  num = 0
  dnm = 0
  for(i in 1:(length(l1))){
    num = num + min(l1[i],l2[i])
    dnm =  dnm + max(l1[i],l2[i])
  }
  return(num/dnm)
}











