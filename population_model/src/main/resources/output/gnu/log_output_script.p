<<<<<<< local
# This file is called log_output_script.p
reset
set terminal pdf
set output 'output.pdf'
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
unset key
set title "Population Change Over Time"
set xlabel "Year"
set ylabel "Population"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/Population.dat" using 1:2 title 'Population Change Over Time' with line
unset style
unset border
unset tics
unset grid
set key
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
unset key
set title "Cohabitations Over Time"
set xlabel "Year"
set ylabel "Number of Cohabitations"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/Cohabitation.dat" using 1:2 title 'Cohabitations Over Time' with line
unset style
unset border
unset tics
unset grid
set key
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
unset key
set title "Cohabitation then Marriages Over Time"
set xlabel "Year"
set ylabel "Number of Cohabiation then Marriages"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationTheMarriage.dat" using 1:2 title 'Cohabitation then Marriages Over Time' with line
unset style
unset border
unset tics
unset grid
set key
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
unset key
set title "Marriages Over Time"
set xlabel "Year"
set ylabel "Number of Marriages"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/Marriage.dat" using 1:2 title 'Marriages Over Time' with line
unset style
unset border
unset tics
unset grid
set key
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children Distribution - Affairs - 1600 - end"
set ylabel "Frequency"
set xlabel "Number of Children"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfAffairs_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfAffairs_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children Distribution - Cohabitation - 1849 - end"
set ylabel "Frequency"
set xlabel "Number of Children"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfCohab_1849.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfCohab_1849.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children Distribution - Cohabitation Then Marriage - 1849 - end"
set ylabel "Frequency"
set xlabel "Number of Children"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfCohabTheMarriage_1849.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfCohabTheMarriage_1849.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children Distribution - Marriage - 1849 - end"
set ylabel "Frequency"
set xlabel "Number of Children"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfMarriage_1849.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfMarriage_1849.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children In Maternity Distribution - 1600 - end"
set ylabel "Frequency"
set xlabel "Number of Children in Maternity"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenInMaternity_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenInMaternity_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Time from Cohabitation to Marriage Distribution - 1600 - end"
set ylabel "Frequency"
set xlabel "Time from Cohabiation to Marriage Distribution"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/TimeFromCohabToMarriage_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/TimeFromCohabToMarriage_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Length of Cohabitation Distribution - 1600 - end"
set ylabel "Frequency"
set xlabel "Length OF Cohabitation"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationLength_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationLength_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Divorce for Males - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Divorce"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDivorceMale_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDivorceMale_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Divorce for Females - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Divorce"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDivorceFemale_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDivorceFemale_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Death Distribution - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Death"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDeath_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDeath_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Cohabitation for Females - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Cohabitation"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationAgeFemales_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationAgeFemales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Cohabitation for Males - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Cohabitation"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationAgeMales_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationAgeMales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Cohabitation then Marriage for Females - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Cohabitation"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationThenMarriageAgeFemales_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationThenMarriageAgeFemales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Cohabitation then Marriage for Males - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Cohabitation"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationThenMarriageAgeMales_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationThenMarriageAgeMales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Marriage for Females - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Marriage"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/MarriageAgeFemales_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/MarriageAgeFemales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Marriage for Males - 1600 - end"
set ylabel "Frequency"
set xlabel "Age At Marriage"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/MarriageAgeMales_1600.dat" using 1:2 title 'Actual' with line, "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/MarriageAgeMales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set ylabel "Frequency"
set xlabel "Divorce Instigation By"
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.95
set xtic scale 0
set xtic rotate by 45 right
set title "Divorce Instigation By Gender - 1974 - end"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceInstigationByGender_1974.dat" using 3:xtic(2) ti col fc rgb '#8b1a0e', "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceInstigationByGender_1974.dat" u 4 ti col fc rgb '#5e9c36'
unset ylabel
unset xlabel
unset style
unset boxwidth
unset xtic
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set ylabel "Frequency"
set xlabel "Divorce Reason"
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.95
set xtic scale 0
set xtic rotate by 45 right
set title "Divorce Reason For Females - 1600 - end"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceReasonFemale_1600.dat" using 3:xtic(2) ti col fc rgb '#8b1a0e', "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceReasonFemale_1600.dat" u 4 ti col fc rgb '#5e9c36'
unset ylabel
unset xlabel
unset style
unset boxwidth
unset xtic
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set ylabel "Frequency"
set xlabel "Divorce Reason"
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.95
set xtic scale 0
set xtic rotate by 45 right
set title "Divorce Reason For Males - 1600 - end"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceReasonMale_1600.dat" using 3:xtic(2) ti col fc rgb '#8b1a0e', "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceReasonMale_1600.dat" u 4 ti col fc rgb '#5e9c36'
unset ylabel
unset xlabel
unset style
unset boxwidth
unset xtic
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set ylabel "Frequency"
set xlabel "Partnership Characteristic"
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.95
set xtic scale 0
set xtic rotate by 45 right
set title "Partnership Characteristic - 2010 - end"
plot "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/PartnershipCharacteristic_2010.dat" using 3:xtic(2) ti col fc rgb '#8b1a0e', "/home/tsd4/digitising_scotland/population_model/src/main/resources/output/gnu/PartnershipCharacteristic_2010.dat" u 4 ti col fc rgb '#5e9c36'
unset ylabel
unset xlabel
unset style
unset boxwidth
unset xtic
set terminal png
reset
=======
# This file is called log_output_script.p
reset
set terminal pdf
set output 'output.pdf'
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
unset key
set title "Population Change Over Time"
set xlabel "Year"
set ylabel "Population"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/Population.dat" using 1:2 title 'Population Change Over Time' with line
unset style
unset border
unset tics
unset grid
set key
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
unset key
set title "Cohabitations Over Time"
set xlabel "Year"
set ylabel "Number of Cohabitations"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/Cohabitation.dat" using 1:2 title 'Cohabitations Over Time' with line
unset style
unset border
unset tics
unset grid
set key
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
unset key
set title "Cohabitation then Marriages Over Time"
set xlabel "Year"
set ylabel "Number of Cohabiation then Marriages"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationTheMarriage.dat" using 1:2 title 'Cohabitation then Marriages Over Time' with line
unset style
unset border
unset tics
unset grid
set key
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
unset key
set title "Marriages Over Time"
set xlabel "Year"
set ylabel "Number of Marriages"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/Marriage.dat" using 1:2 title 'Marriages Over Time' with line
unset style
unset border
unset tics
unset grid
set key
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children Distribution - Affairs - 1600 - end"
set ylabel "Frequency"
set xlabel "Number of Children"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfAffairs_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfAffairs_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children Distribution - Cohabitation - 1849 - end"
set ylabel "Frequency"
set xlabel "Number of Children"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfCohab_1849.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfCohab_1849.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children Distribution - Cohabitation Then Marriage - 1849 - end"
set ylabel "Frequency"
set xlabel "Number of Children"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfCohabTheMarriage_1849.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfCohabTheMarriage_1849.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children Distribution - Marriage - 1849 - end"
set ylabel "Frequency"
set xlabel "Number of Children"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfMarriage_1849.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenNumberOfMarriage_1849.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Number of Children In Maternity Distribution - 1600 - end"
set ylabel "Frequency"
set xlabel "Number of Children in Maternity"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenInMaternity_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/ChildrenInMaternity_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Time from Cohabitation to Marriage Distribution - 1600 - end"
set ylabel "Frequency"
set xlabel "Time from Cohabiation to Marriage Distribution"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/TimeFromCohabToMarriage_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/TimeFromCohabToMarriage_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Length of Cohabitation Distribution - 1600 - end"
set ylabel "Frequency"
set xlabel "Length OF Cohabitation"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationLength_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationLength_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Divorce for Males - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Divorce"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDivorceMale_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDivorceMale_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Divorce for Females - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Divorce"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDivorceFemale_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDivorceFemale_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Death Distribution - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Death"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDeath_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/AgeAtDeath_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Cohabitation for Females - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Cohabitation"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationAgeFemales_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationAgeFemales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Cohabitation for Males - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Cohabitation"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationAgeMales_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationAgeMales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Cohabitation then Marriage for Females - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Cohabitation"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationThenMarriageAgeFemales_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationThenMarriageAgeFemales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Cohabitation then Marriage for Males - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Cohabitation"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationThenMarriageAgeMales_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/CohabitationThenMarriageAgeMales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Marriage for Females - 1600 - end"
set ylabel "Frequency"
set xlabel "Age at Marriage"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/MarriageAgeFemales_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/MarriageAgeFemales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set style line 1 lc rgb '#8b1a0e' pt 1 ps 1 lt 1 lw 20 # --- red
set style line 2 lc rgb '#5e9c36' pt 6 ps 1 lt 1 lw 20 # --- green
set title "Age at Marriage for Males - 1600 - end"
set ylabel "Frequency"
set xlabel "Age At Marriage"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/MarriageAgeMales_1600.dat" using 1:2 title 'Actual' with line, "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/MarriageAgeMales_1600.dat" using 1:3 title 'Dist' with line
unset style
unset border
unset tics
unset grid
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set ylabel "Frequency"
set xlabel "Divorce Instigation By"
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.95
set xtic scale 0
set xtic rotate by 45 right
set title "Divorce Instigation By Gender - 1974 - end"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceInstigationByGender_1974.dat" using 3:xtic(2) ti col fc rgb '#8b1a0e', "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceInstigationByGender_1974.dat" u 4 ti col fc rgb '#5e9c36'
unset ylabel
unset xlabel
unset style
unset boxwidth
unset xtic
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set ylabel "Frequency"
set xlabel "Divorce Reason"
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.95
set xtic scale 0
set xtic rotate by 45 right
set title "Divorce Reason For Females - 1600 - end"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceReasonFemale_1600.dat" using 3:xtic(2) ti col fc rgb '#8b1a0e', "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceReasonFemale_1600.dat" u 4 ti col fc rgb '#5e9c36'
unset ylabel
unset xlabel
unset style
unset boxwidth
unset xtic
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set ylabel "Frequency"
set xlabel "Divorce Reason"
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.95
set xtic scale 0
set xtic rotate by 45 right
set title "Divorce Reason For Males - 1600 - end"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceReasonMale_1600.dat" using 3:xtic(2) ti col fc rgb '#8b1a0e', "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/DivorceReasonMale_1600.dat" u 4 ti col fc rgb '#5e9c36'
unset ylabel
unset xlabel
unset style
unset boxwidth
unset xtic
set style line 11 lc rgb '#808080' lt 1
set border 3 back ls 11
set tics nomirror
set style line 12 lc rgb '#808080' lt 0 lw 1
set grid back ls 12
set ylabel "Frequency"
set xlabel "Partnership Characteristic"
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.95
set xtic scale 0
set xtic rotate by 45 right
set title "Partnership Characteristic - 2010 - end"
plot "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/PartnershipCharacteristic_2010.dat" using 3:xtic(2) ti col fc rgb '#8b1a0e', "E:/DS/digitising_scotland/population_model/src/main/resources/output/gnu/PartnershipCharacteristic_2010.dat" u 4 ti col fc rgb '#5e9c36'
unset ylabel
unset xlabel
unset style
unset boxwidth
unset xtic
set terminal png
reset
>>>>>>> other
