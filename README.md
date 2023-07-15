# nlpTweets
This program uses a dataset of tweets to (1) find the most common topics discussed, and (2) analyze the sentences to detect sentiment (how positive or negative the sentence was). Was a class project.

Will also need to download 
https://nlp.stanford.edu/software/stanford-corenlp-full-2018-10-05.zip
unzip in terminal: 
unzip stanford-corenlp-full-2018-10-05.zip
cd stanford-corenlp-full-2018-10-05
cp ejml-0.23.jar ..
cp stanford-corenlp-full-2018-10-05/stanford-corenlp-3.9.2.jar ..
cd ..

To use stanford libraries:
javac -classpath ".:./ejml-0.23.jar:./stanford-corenlp-3.9.2.jar:./stanford-english-corenlp-2018-10-05-models.jar" *.java 
for Mac/linux, or
javac -classpath ".\stanford-corenlp-3.9.2.jar;.;./ejml-0.23.jar;./stanford-english-corenlp-2018-10-05-models.jar" *.java 
for Windows.

and then

java -classpath ".:./ejml-0.23.jar:./stanford-corenlp-3.9.2.jar:./stanford-english-corenlp-2018-10-05-models.jar" Driver 
for Mac/linux, or
java -classpath ".\stanford-corenlp-3.9.2.jar;.;./ejml-0.23.jar;./stanford-english-corenlp-2018-10-05-models.jar" Driver 
for Windows.
