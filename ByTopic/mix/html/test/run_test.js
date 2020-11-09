import path from 'path';
import fs from 'fs';
import vm from 'vm';
import child_process from 'child_process';

function listTestScripts(rootPath) {
    let dirents = fs.readdirSync(rootPath, {'withFileTypes': true});

    let fileNames = [];
    dirents.forEach(dirent => {
        if (dirent.isFile() && dirent.name.endsWith(".test.js")) {
            fileNames.push(path.join(rootPath, dirent.name));
        } else if (dirent.isDirectory() && dirent.name != 'node_modules') {
            fileNames = fileNames.concat(listTestScripts(path.join(rootPath, dirent.name)));
        }
    });

    return fileNames;
}

function runTestScript(fileName) {
    let command = `node --trace-uncaught --experimental-vm-modules ${fileName}`;
    child_process.exec(command, (error, stdout, stderr) => {
        if (error != null) {
            console.warn(error, stdout, stderr);
        } else if (stdout.length > 0 || stderr.length > 0) {
            console.log(stdout, stderr);
        }
    });
}

function runTest() {
    listTestScripts(path.resolve('./')).forEach(file => runTestScript(file));
}

runTest();
