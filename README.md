### Stateless Authorization Tokens

1. CSRF Token - used only at the login page
 - Validity is 5 minutes

2. Soft Authorization Token - used for each request after user authentication 
 - Validity is permanent
 - Bound to username + browser

#### Token Samples

```bash
Java

JS CSRF Token: 1713761538271.Mx6rcI7E2s75Be4N.R5wPe1_OoLzStu3yYkse98lm0mAwqdxw1XnvGLLcTTw
Valid: true
JS Login Token: bWloYWVsYQ.Mx6rcI7E2s75Be4N.hBKv2vroc3YEB-nGr8Lky8LtQGJy8M1q2eAMIrUA6ak
Valid: true

---------------------------
JavaScript

JS CSRF Token: 1713761538271.Mx6rcI7E2s75Be4N.R5wPe1_OoLzStu3yYkse98lm0mAwqdxw1XnvGLLcTTw
Valid: true
JS Login Token: bWloYWVsYQ.Mx6rcI7E2s75Be4N.hBKv2vroc3YEB-nGr8Lky8LtQGJy8M1q2eAMIrUA6ak
Valid: true

---------------------------
Go

Go CSRF Token: 1713761528921.Mx6rcI7E2s75Be4N.R5wPe1_OoLzStu3yYkse98lm0mAwqdxw1XnvGLLcTTw
Valid: true
Go Login Token: bWloYWVsYQ.Mx6rcI7E2s75Be4N.hBKv2vroc3YEB-nGr8Lky8LtQGJy8M1q2eAMIrUA6ak
Valid: true
```