var path = require('path');
var { DefinePlugin } = require('webpack');
var dotenv = require('dotenv').config({ path: __dirname + '/.env' });

module.exports = {
    node: {
        fs: 'empty',
    },

    entry: {
        app: [
            '@babel/polyfill',
            'react-hot-loader/webpack',
            'webpack-dev-server/client?http://localhost:8080',
            'webpack/hot/only-dev-server',
            './src/main/frontend/index.js',
        ],
    },
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: path.resolve(__dirname, './src/main/resources/static/built/'),
        filename: 'bundle.js',
        publicPath: '/built/',
    },
    devServer: {
        hot: true,
        contentBase: [
            path.resolve(__dirname, '.'),
            path.resolve(__dirname, './src/main/resources/static/built'),
        ],
        proxy: {
            '/': {
                target: {
                    host: 'localhost',
                    protocol: 'http:',
                    port: 8081,
                },
            },
            ignorePath: true,
            changeOrigin: true,
            secure: false,
        },
        publicPath: '/built/',
        port: 8080,
        host: 'localhost',
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [
                    {
                        loader: 'babel-loader',
                        options: {
                            presets: [
                                '@babel/preset-env',
                                '@babel/preset-react',
                            ],
                        },
                    },
                ],
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.(png|jpg|jpeg|gif)$/,
                loader: 'file-loader',
                options: {
                    publicPath: 'images/',
                    outputPath: './src/main/resources/static/images',
                    useRelativePath: true,
                },
            },
        ],
    },
    plugins: [
        new DefinePlugin({
            'process.env': JSON.stringify(dotenv.parsed),
        }),
    ],
};
