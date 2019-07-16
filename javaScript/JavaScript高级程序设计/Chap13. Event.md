[TOC]

# Event

## EVENT FLOW

<u>*Event flow* describes the order in which events are received on the page.</u>

### Event Bubbling

### Event Capturing

### DOM Event Flow

Three phase:

- the event capturing phase

-  at the target

-  the event bubbling

  

## EVENT HANDLERS

### HTML Event Handlers

### DOM Level 0 Event Handlers

Each element (as well as `window` and `document`) has event handler properties that are typically all **lowercase**, such as `onclick`.

###  DOM Level 2 Event Handlers

DOM Level 2 Events define two methods to deal with the assignment and removal of event handlers: **`addEventListener()`** and **`removeEventListener()`**.

accept three arguments: **the event name to handle**, **the event handler function**, and a
**Boolean value** indicating <u>whether to call the event handler during the capture phase (true) or during the bubble phase (false).</u>

