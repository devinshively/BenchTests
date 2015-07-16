var restify = require('restify');
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(':memory:');
db.run("CREATE TABLE documents (id TEXT PRIMARY KEY, title TEXT, text TEXT)", function(err, rows) {
  if(err) {
    console.log("%j",err);
  }
});
var uuid = require('node-uuid');

function respond(req, res, next) {
  //res.send('hello ' + req.params.name);
  res.json({response: "hello"})
  next();
}

function find(req, res, next) {
  console.log("%s",req.params.id);
  var doc = db.all("select * from documents where id = ?", req.params.id, function(err, rows) {
      if(err) {
        var message = rows.length > 0 ? "Viewing "
             + db_name + '/' + table
             : "No data found in table '" + table + "' in " + db_name;

        res.render('table_view', {message: message, rows: rows});
        res.send(404);
        next();
      } else {
        res.json(rows);
        next();
      }
    }
  );
}

function findAll(req, res, next) {
  var doc = db.all("select * from documents", function(err, rows) {
      if(err) {
        var message = rows.length > 0 ? "Viewing "
             + db_name + '/' + table
             : "No data found in table '" + table + "' in " + db_name;

        res.render('table_view', {message: message, rows: rows});
        res.send(404);
        next();
      } else {
        console.log(rows.length);
        res.json(rows);
        next();
      }
    }
  );
}

function create(req, res, next) {
  var doc = req.body;
  docId = uuid.v4();
  doc = {id:docId, title:doc.title, text:doc.text}
  db.run("insert or ignore into documents(id, title, text) values (?, ?, ?)", doc.id, doc.title, doc.text, function(err, rows) {
  if(err) {
    console.log("%j",err);
  }
});
  req.params.id = docId;
  return find(req, res, next);
}

function remove(req, res, next) {
  db.run("delete from documents where id = ?", req.params.id);
  res.send(200);
  next();
}

function update(req, res, next) {
  var doc = req.body;
  doc.id = req.params.id;
  db.run("update documents set title = ?, text = ? where id = ?", doc.title, doc.text, doc.id);
  return find(req, res, next);
}

var server = restify.createServer();
server.use(restify.bodyParser());
server.get('/hello/:name',respond);
server.post('/documents',create);
server.get('/documents',findAll);
server.get('/documents/:id',find);
server.put('/documents/:id',update);
server.del('/documents/:id',remove);

server.listen(8080, function() {
  console.log('%s listening at %s', server.name, server.url);
});
