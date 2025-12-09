import "./App.css";
import { Button } from "./components/ui/button";
import { Calendar } from "./components/ui/calendar";
function App() {
  return (
    <div className="p-10">
      <h1 className="text-3xl font-bold">Hello auth frontend app</h1>
      <Button variant={"destructive"}>Click me</Button>
      <Calendar />
    </div>
  );
}

export default App;
