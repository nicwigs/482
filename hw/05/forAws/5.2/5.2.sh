wget http://www.cse.msu.edu/~cse482/lastFM.tar

tar -xvf lastFM.tar

cd lastFM

pig -x local -4 ../nolog.conf ../q2a.pig
pig -x local -4 ../nolog.conf ../q2b.pig
pig -x local -4 ../nolog.conf ../q2c.pig
pig -x local -4 ../nolog.conf ../q2d.pig


cat q2a/* > q2a.txt
cat q2b/* > q2b.txt
cat q2c/* > q2c.txt
cat q2d/* > q2d.txt
