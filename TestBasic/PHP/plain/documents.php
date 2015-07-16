<?php

  function paths($url) {
      $uri = parse_url($url);
      return $uri['path'];
  }

  $pdo = new PDO('pgsql:host=localhost;dbname=test;', 'test', 'password', array(
                  PDO::ATTR_PERSISTENT => true
              ));
  $uri = $_SERVER['REQUEST_URI'];
  $method = $_SERVER['REQUEST_METHOD'];
  $paths = explode('/', paths($uri));
  array_shift($paths);
  $resource = array_shift($paths);
  $id = array_shift($paths);

  if(empty($id) && $method == 'GET') {
      header('Content-type: application/json');
      $statement = $pdo->query('SELECT * FROM documents');
      echo json_encode($statement->fetchAll(PDO::FETCH_ASSOC));

  } else if(empty($id) && $method == 'POST') {
    $data = json_decode(file_get_contents('php://input'));
    if (is_null($data)) {
        header('HTTP/1.1 400 Bad Request');
    } else {
      header('Content-type: application/json');
      if(empty($data->id)) {
        $statement = $pdo->prepare('INSERT INTO documents(title,text) VALUES(:title,:text)');
        $statement->execute(array(':title' => $data->title, ':text' => $data->text));
        $data->id = $pdo->lastInsertId();
        echo json_encode($data);
      } else {
        $statement = $pdo->prepare('UPDATE documents SET title = :title, text = :text WHERE id=:id');
        $statement->execute(array('title' => $data->id, ':title' => $data->title, ':text' => $data->text));
        echo json_encode($data);
      }
    }


  } else if(!empty($id) && $method == 'GET') {
      header('Content-type: application/json');
      $statement = $pdo->prepare('SELECT * FROM documents where id=:id');
      $statement->execute(array(':id' => $id));
      echo json_encode($statement->fetchAll(PDO::FETCH_ASSOC));

  } else if(!empty($id) && $method == 'DELETE') {
      $statement = $pdo->prepare('DELETE FROM documents WHERE id=:id');
      $statement->execute(array(':id' => $id));
      if($statement->rowCount() > 0) {
          header('HTTP/1.1 204 No Content');
      } else {
          header('HTTP/1.1 404 Not Found');
      }

  } else {
      header('HTTP/1.1 405 Method Not Allowed');
      header('Allow: GET, POST, DELETE');
  }


?>
