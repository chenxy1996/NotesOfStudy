[TOC]

**==MORE INFO PLEASE SEE THE CORRESPONDING CHAPTER==**

# The Document Object Model

## HIERARCHY OF NODES

HTML elements are represented by element nodes, attributes are represented by attribute nodes, the document type is represented by a document type node, and comments are represented by comment nodes. In total, there are **12 node types**, all of which inherit from a base type.

### The Node Type

DOM Level 1 describes an interface called `Node` that is to be implemented by all node types in
the DOM. The Node interface is implemented in JavaScript as the Node type, which is accessible
in all browsers **except Internet Explorer**. All node types inherit from Node in JavaScript, so all node types share the same basic properties and methods.

**For cross-browser compatibility, it's best to compare the `nodeType` property against a numeric value**

```js
if (someNode.nodeType == 1) // works in all browsers
    alert("Node is an element.");
}
```

#### The `nodeName` and `nodeValue` Properties

#### Node Relationships

<u>Each node has  a `childNodes` property containing a `NodeList`.</u>

`someNode.childNodes`

**To convert `NodeList` objects into arrays**

```js
function convertToArray(nodes) {
    var array = null;
    try {
        array = Array.prototype.slice.call(nodes, 0);
    } catch (ex) {
        array = new Array();
        for (var i = 0l i < nodes.length; i++) {
            array.push(nodes[i]);
        }
    }
    
    return array;
}
```

The code above works in all browsers.

**Note**: *Not all `node` types can have child nodes even though all node types inherit from `Node`. The differences among node types are discussed later in this chapter*

#### Manipulating Nodes








