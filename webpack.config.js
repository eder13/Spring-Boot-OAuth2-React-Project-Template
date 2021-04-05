var path = require('path');
var { DefinePlugin } = require('webpack');
var dotenv = require('dotenv').config({path: __dirname + '/.env'});

module.exports = {
    entry: {
        app: ['@babel/polyfill', './src/main/frontend/index.js']
    },
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
            {
                test: /\.css$/,
                use: [
                    'style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.(png|jpg|jpeg|gif)$/,
                loader: 'file-loader',
                options: {
                    publicPath: 'images/',
                    outputPath: './src/main/resources/static/images',
                    useRelativePath: true
                }
            }
        ],
    },
    plugins: [
        new DefinePlugin({
            'process.env': JSON.stringify(dotenv.parsed),
        })
    ],
};