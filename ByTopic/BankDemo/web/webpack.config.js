const path = require('path')

module.exports = {
    entry: './src/index.js',
    output: {
	filename: 'index.js',
	path: path.resolve(__dirname, 'build')
    },
    mode: 'development',
    module: {
	rules: [
	    { test: /\.css$/, use: 'css-loader' },
	    { test: /\.ts$/, use: 'ts-loader' },
	    { test: /\.(eot|woff|woff2|svg|ttf)([\?]?.*)$/, loader: "file-loader" }
	]
    }
}
