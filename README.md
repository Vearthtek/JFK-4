# JFK-4

## Scripting in Java

Javascript [Rhino](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino)

Python [Jython](http://www.jython.org/)

In `File (.csv)` tab, user can add new records by double clicking below existing records.

After successfully executing script, variable `entities` is saved to file.

### JS scripts:
### 1.
```javascript
entities.forEach(function(e) {
  e.salary += 500;
})
```
### 2.
```javascript
for(var i = 1; i < 5; ++i) {
  entities.push(new Entity('Pracownik ' + i, 'Nazwisko ' + i, 2000 + i * 100, 'pracownik' + i + '@example.com'));
}
var s = 0;
entities.forEach(function(e) {
  s += e.salary;
})
s / entities.length
```

### Python scripts:
### 3.

```python
for e in entities:
    e.email = e.firstName[0].lower() + '.' + e.lastName.lower() + '@example.com'
    print(e.firstName + ' ' + e.lastName + ' ' + e.email)
```

### 4.
```python
for e in entities:
    e.email = e.email.replace('com', 'net')
    e.salary *= 2;
    print(e.firstName + ' ' + e.lastName + ' ' + e.email + ' ' + str(e.salary))
```

### 5.
```python
s = 0
for e in entities:
    s += e.salary
s = s /len(entities)
for e in entities:
    e.salary = s
    print(e.firstName + ' ' + str(e.salary))
```