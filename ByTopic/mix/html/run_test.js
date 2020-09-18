import path from 'path';
import fs from 'fs';
import vm from 'vm';
import child_process from 'child_process';

function listTestScripts(callback) {
    var rootPath = path.resolve('./');
    fs.readdir(rootPath, (err, files) => {
        if (err) {
            console.warn(err);
            return;
        }

        let scripts = [];
        files.forEach((fileName) => {
            if (fileName.endsWith(".test.js")) {
                scripts.push(path.join(rootPath, fileName));
            }
        });

        callback(scripts);
    });
}

function runTestScript(fileName) {
    // console.log(fileName);
    let command = `node --experimental-vm-modules ${fileName}`;
    child_process.exec(command, (error, stdout, stderr) => {
        if (error != null) {
            console.warn(error, stdout, stderr);
        } else if (stdout.length > 0 || stderr.length > 0) {
            console.log(stdout, stderr);
        }
    });
}

function runTest() {
    listTestScripts((files) => {
        files.forEach((file) => {
            runTestScript(file);
        });
    });
}

runTest();
