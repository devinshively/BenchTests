var express = require('express');
// var logger = require('morgan');
// var bodyParser = require('body-parser');
var app = express();
// app.use(bodyParser.urlencoded({extended: true}));
// app.use(bodyParser.json());
// app.use(logger('dev'));
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/demo');
var Network = mongoose.model('Network', {strict:false});


app.get('/:ssid', function(req, res) {
  var ssid = req.params.ssid;
  Network.findOne({ssid:ssid},function(err, network) {
    if (err) res.send(error);
    res.send(network);
  });
});

var server = app.listen(8080, function() {
  var host = server.address().address;
  var port = server.address().port;
  console.log('Skynet listening at http://%s:%s', host, port);
});
