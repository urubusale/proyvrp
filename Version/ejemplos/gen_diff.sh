#!/bin/bash

c=1000
d=50
max_x=10000;
max_y=10000;
MINPARAMS=1;
RANDOM=1234
if [ $# -lt "$MINPARAMS" ]
then
  echo
  echo "This script needs at least $MINPARAMS command-line arguments"
  echo "First parameter is the increse porcentage of capacity avaiable per dept"
  exit 0
fi  

porcentaje=$1

echo "NAME : gil000" 
echo "COMMENT : (Pepe)" 
echo "TYPE : MD-VRP" 
echo "DIMENSION : " $((c+d))
echo "EDGE_WEIGHT_TYPE : EUC_2D" 
echo "CAPACITY : 500"
echo "NODE_COORD_SECTION" 

for ((i=1; i<=c+d; i++));  do echo $i $((RANDOM%max_x)) $((RANDOM%max_y)); done
captot=0;
echo DEMAND_SECTION
for ((i=1; i<=c; i++)); 
   do
       temp=$((RANDOM%32+25));	 
       echo $i $temp;
       captot=$((captot+temp));
   done
capdep=$((captot/d))
capdeppor=$((capdep*porcentaje))
capdeppon=$((capdeppor/100))
capdepponderado=$((capdep+capdeppon)) 
capdep_sum=0;
# echo $captot $capdep $capdeppor $capdeppon $capdepponderado   
for((i=c+1; i<=c+d; i++)); 
do
	randcap=$((captot/d))
	capdep=$((RANDOM%randcap+randcap/2))
	capdep_sum=$((capdep_sum+capdep))
	capdeppor=$((capdep*porcentaje))
	capdeppon=$((capdeppor/100))
	capdepponderado=$((capdep+capdeppon)) 
	echo $i $capdepponderado; 
done
echo DEPOT_SECTION
for ((i=c+1; i<=c+d; i++)); do echo $i; done
echo  -1
