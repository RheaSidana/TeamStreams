import { QueryClient, QueryClientProvider } from "react-query";

import "./App.css";
import Layout from "./components/layout/Layout";

const queryClient = new QueryClient({});

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <div className="App">
        <Layout />
      </div>
    </QueryClientProvider>
  );
}

export default App;
