import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { motion } from "framer-motion";
import { BarChart3, User, ShieldCheck, Activity } from "lucide-react";
import { getCurrentUser } from "@/services/AuthService";
import useAuth from "@/auth/store";
import { useState } from "react";
import type UserT from "@/models/User";
import toast from "react-hot-toast";

function Userhome() {
  const user = useAuth((state) => state.user);
  const [user1, setUser1] = useState<UserT | null>(null);

  const getUserData = async () => {
    try {
      const user1 = await getCurrentUser(user?.email);

      setUser1(user1);
      toast.success("you are able to access secured apis")
    } catch (error) {
      console.log(error);
      toast.error("error in getting data");
    }
  };

  return (
    <div className="min-h-screen bg-background text-foreground p-6">
      {/* Page Title */}
      <motion.h1
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
        className="text-4xl font-bold mb-8"
      >
        Dashboard Overview
      </motion.h1>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">
        {[
          {
            title: "Total Logins",
            value: "1,245",
            icon: <User className="w-8 h-8 text-primary" />,
          },
          {
            title: "Security Score",
            value: "98%",
            icon: <ShieldCheck className="w-8 h-8 text-primary" />,
          },
          {
            title: "Active Sessions",
            value: "12",
            icon: <Activity className="w-8 h-8 text-primary" />,
          },
        ].map((stat, i) => (
          <motion.div
            key={i}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: i * 0.1 }}
          >
            <Card className="bg-card/70 backdrop-blur-lg border-border rounded-2xl shadow-lg">
              <CardContent className="p-6 flex items-center gap-4">
                <div className="p-3 bg-muted rounded-xl">{stat.icon}</div>
                <div>
                  <p className="text-muted-foreground text-sm">{stat.title}</p>
                  <h3 className="text-2xl font-semibold">{stat.value}</h3>
                </div>
              </CardContent>
            </Card>
          </motion.div>
        ))}
      </div>

      {/* Activity Section */}
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.6, delay: 0.2 }}
      >
        <Card className="bg-card/70 backdrop-blur-lg border-border rounded-2xl shadow-lg mb-10">
          <CardContent className="p-6">
            <h2 className="text-xl font-semibold mb-4 flex items-center gap-2">
              <BarChart3 className="w-6 h-6 text-primary" /> Recent Activity
            </h2>
            <ul className="space-y-3 text-muted-foreground">
              <li>• Logged in from Chrome (Windows)</li>
              <li>• Password updated</li>
              <li>• New device added to trusted list</li>
              <li>• Logged out from Safari (iPhone)</li>
            </ul>
          </CardContent>
        </Card>
      </motion.div>

      {/* Dummy CTA */}
      <div className="text-center">
        <Button onClick={getUserData} className="rounded-2xl px-8 text-lg">
          Get current user
        </Button>

        <p>{user1?.name}</p>
      </div>
    </div>
  );
}

export default Userhome;
