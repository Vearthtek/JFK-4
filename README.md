# JFK-4

## Scripting in Java

Javascript [Rhino](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino)
Python [Jython](http://www.jython.org/)

### JS scripts:
#### 1
```javascript
entities.forEach(function(e){
  e.salary += 500;
})
```
#### 2
```javascript
for(var i = 1; i < 5; ++i) {
  entities.push(new Entity('Pracownik ' + i, 'Nazwisko ' + i, 2000 + i * 100, 'pracownik' + i + '@example.com'));
}
var s = 0;
entities.forEach(function(e){
  s += e.salary;
})
s / entities.length
```

### Python scripts:
#### 3
```python
#TODO
```

#### 4
```python
#TODO
```