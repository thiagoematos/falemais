
const routes = [
  {
    path: '/',
    component: () => import('layouts/MyLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') },
      { path: 'tarifas', component: () => import('pages/Rates.vue') },
      { path: 'planos', component: () => import('pages/Plans.vue') },
      { path: 'ddds', component: () => import('pages/Ddds.vue') }
    ]
  }
]

// Always leave this as last one
if (process.env.MODE !== 'ssr') {
  routes.push({
    path: '*',
    component: () => import('pages/Error404.vue')
  })
}

export default routes
