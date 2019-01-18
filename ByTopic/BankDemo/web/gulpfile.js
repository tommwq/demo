/*
  调用webpack打包
  复制到public目录
  调用spring-boot:run
  https://www.jianshu.com/p/9724c47b406c
*/

const child_process = require("child_process");
const string_decoder = require("string_decoder");

const gulp = require("gulp");
const webpack = require("webpack");

const localConfig = require("./localConfig.js");


const pack = function(cb) {
    gulp.src("src/index.html")
	.pipe(gulp.dest("build/"));

    const config = require("./webpack.config.js");
    webpack(config, function(err, stats) {
	if (err != null) {
	    console.err(err, stats);
	}
	cb();
    });
};

const embed = function(cb) {
    const destination = "../server/src/main/resources/public/";
    const source = "build/**";
    gulp.src(source)
	.pipe(gulp.dest(destination));
    gulp.src("src/index.html")
	.pipe(gulp.dest(destination));
    cb();
};

const run = function(cb) {
    const mvnExec = `${localConfig.mvnRoot}/mvn.cmd`;
    const mvnProc = child_process.spawn("cmd.exe", ["/c", mvnExec, "clean", "spring-boot:run"], {
	cwd: "../server",
	detached: false,
	stdio: "pipe"
    });

    const decoder = new string_decoder.StringDecoder("utf8");

    mvnProc.stdout.on('data', (chunk) => {
	console.log(decoder.write(chunk));
    });
    mvnProc.stderr.on('data', (chunk) => {
	console.log(decoder.write(data));
    });
    mvnProc.stdin.end();
    mvnProc.on('close', (exitCode) => {
	cb();
    });
};

module.exports = {
    default: gulp.series(pack, embed),
    run: run
}
