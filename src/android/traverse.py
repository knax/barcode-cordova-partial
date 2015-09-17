# Import the os module, for the os.walk function
import os
 
# Set the directory you want to start from
rootDir = '.'
for dirName, subdirList, fileList in os.walk(rootDir):
    # print 'Found directory: ' + dirName
    dirNameCleaned = dirName[1:]
    for fname in fileList:
        if (fname[-4:] == 'java'):
            print '<source-file src="src/android' + dirNameCleaned + '/' + fname + '"'
            print 'target-dir="src' + dirNameCleaned + '"/>'